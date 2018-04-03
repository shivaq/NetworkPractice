import os
import boto3
import collections
from time import sleep
from datetime import datetime, timedelta
from botocore.client import ClientError
from logging import getLogger, INFO

logger = getLogger()

ec2_client = boto3.client('ec2')

def lambda_handler(event, context):
    descriptions = create_image()
    delete_old_images(descriptions)

def create_image():
    instances = get_instances(['AMI-Backup-Generation'])
    descriptions = {}
    for instance in instances:
        tags = { tag['Key']: tag['Value'] for tag in instance['Tags'] }
        generation = int( tags.get('AMI-Backup-Generation', 0) )

        if generation < 1:
            continue

        instance_id = instance.get('InstanceId')
        create_data_jst = (datetime.now() + timedelta(hours=9)).strftime("%Yy%mm%dd_%Hh%Mm%Ss")
        ami_name = '%s_%s' % (tags['Name'], instance_id)
        ami_name = ami_name + "_" + create_data_jst
        instance_id = instance_id

        image_id = _create_image(instance_id, ami_name, instance_id)
        logger.info('Create Image: ImageId:%s (%s) ' % (image_id['ImageId'], ami_name))
        print('Create Image: ImageId:%s (%s) ' % (image_id['ImageId'], ami_name))
        descriptions[instance_id] = generation

    return descriptions

def get_instances(tag_names):
    reservations = ec2_client.describe_instances(
        Filters=[
            {
                'Name': 'tag-key',
                'Values': tag_names
            }
        ]
    )['Reservations']

    return sum([
        [instance for instance in reservation['Instances']]
        for reservation in reservations
    ], [])

def _create_image(instance_id, ami_name, id_and_generations):
    for i in range(1, 3):
        try:
            return ec2_client.create_image(
                Description = id_and_generations,
                NoReboot = True,
                InstanceId = instance_id,
                Name = ami_name
                )
        except ClientError as e:
            logger.exception(str(e))
            print(str(e))
        sleep(2)
    raise Exception('cannot create image ' + ami_name)

def delete_old_images(descriptions):
    images_descriptions = get_images_descriptions(list(descriptions.keys()))
    for id_and_generations, images in images_descriptions.items():
        delete_count = len(images) - descriptions[id_and_generations]
        if delete_count <= 0:
            continue

        images.sort(key=lambda x:x['CreationDate'])
        old_images = images[0:delete_count]

        for image in old_images:
            _deregister_image(image['ImageId'])
            logger.info('Deregister Image: ImageId:%s (%s)' % (image['ImageId'], image['Description']))
            print('Deregister Image: ImageId:%s (%s)' % (image['ImageId'], image['Description']))

def get_images_descriptions(descriptions):
    images = ec2_client.describe_images(
        Owners = [
            os.environ['AWS_ACCOUNT']
        ],
        Filters = [
            {
                'Name': 'description',
                'Values': descriptions,
            }
        ]
    )['Images']

    groups = collections.defaultdict(lambda: [])
    { groups[ image['Description'] ].append(image) for image in images }

    return groups

def _deregister_image(image_id):
    for i in range(1, 3):
        try:
            return ec2_client.deregister_image(
                ImageId = image_id
            )
        except ClientError as e:
            logger.exception(str(e))
            print(str(e))
        sleep(2)
    raise Exception('Cannot Deregister image: ' + image_id)
