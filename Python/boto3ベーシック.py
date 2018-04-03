▼ Session
-------------------------------------------------
# AWSサービス との接続を開始する部分。

aws configure を指定して resource 取得セッションを開始
# my_east_sesison = boto3.session(region_name = 'us-east-1')
# video_s3 = my_east_sesison.resource('s3')

aws configure を指定して client 取得セッションを開始
# backup_s3c = my_west_session.client('s3')
# もしくは
# video_s3c = boto3.client("s3", region_name = 'us-east-1')

configure 指定せずに resource や client を取得
# ec2 = boto3.client('ec2')
# ec2 = boto3.resource('ec2')
-------------------------------------------------



▼ Resource
-------------------------------------------------
ハイレベルな、抽象化されたサービスクラス。
特定のターゲットを指定せず、EC2 などの aws サービスリソースをを扱うことができる。
アクションの中には、Client はカバーしていても、Resource はカバーしていない場合があるので、
そういう時は Client を使うといい。
# s3_resource = boto3.resource("s3")
# backup_bucket = s3_resource.Bucket('backupbucket')
#
# # just pass the instantiated bucket object
# def list_bucket_contents(bucket):
#    for object in bucket.objects.all():
#       print(object.key)
#
# list_bucket_contents(backup_bucket)
-------------------------------------------------

▼ Client
-------------------------------------------------
ロウレベルなサービスクラス。
特定のターゲットを指定して使う。
# s3 = boto3.client('s3')
#
# def list_bucket_contents(bucket_name):
#    for object in s3.list_objects_v2(Bucket=bucket_name) :
#       print(object.key)
#
# list_bucket_contents('Mybucket')
-------------------------------------------------


# セッションを指定せずに
# boto3.client("some-seriece") や boto3.resource("some-seriece") を取得する時は、
# デフォルトの aws configure を使用している

-------------------------------------------------

-------------------------------------------------



client
# EC2 オブジェクトみたいなものを下記のように取得する。
# client = boto3.client('ec2')

describe_instances(**kwargs)
Filters (list)
# ・下記情報を元にフィルタリングされたインスタンスを返す
# タグのキー、VPC-id, ENI の状態、IPアドレスなどなど
    # Filters=[
    #     {
    #         'Name': 'string',
    #         'Values': [
    #             'string',
    #         ]
    #     },
    # ],

    InstanceIds=[
        'string',
    ],
    DryRun=True|False,
    MaxResults=123,
    NextToken='string'
-------------------------------------------------







▼ タグのキー及び値を指定してインスタンス取得
-------------------------------------------------
# ec2_resource = boto3.resource("ec2")
#
# def filter_instances(project):
#     instances = []
#
#     if project:
#         filters = [{'Name': 'tag:Project', 'Values':[project]}]
#         instances = ec2.instances.filter(Filters=filters)
#     else:
#         instances = ec2.instances.all()
#
#     return instances
-------------------------------------------------

▼ インスタンスを全部取得 →情報を列挙
-------------------------------------------------
# ec2_resource = boto3.resource('ec2')
# instances = ec2_resource.instances.all()
#
#     for i in instances:
#         # リストでカプセル化された辞書を decapsulate して変数に格納
#         tags = { t['Key']: t['Value'] for t in i.tags or []}
#         print(', '.join((
#             i.id,
#             i.instance_type,
#             i.placement['AvailabilityZone'],
#             i.state['Name'],
#             i.public_dns_name,
#             i.image_id,
#             # 右側はデフォ値
#             tags.get('Name', '<no project>'))))
-------------------------------------------------

ec2_resource = boto3.resource('ec2')
