ec2_client   = boto3.client('ec2')
response = ec2_client.describe_instances()
response["Reservations"]

[{'Groups': [],
  'Instances': [{'AmiLaunchIndex': 0,
    'Architecture': 'x86_64',
    'BlockDeviceMappings': [{'DeviceName': '/dev/xvda',
      'Ebs': {'AttachTime': datetime.datetime(2018, 4, 1, 14, 10, 41, tzinfo=tzutc()),
       'DeleteOnTermination': True,
       'Status': 'attached',
       'VolumeId': 'vol-0f5af8283b942f7fd'}}],
    'ClientToken': '',
    'EbsOptimized': False,
    'EnaSupport': True,
    'Hypervisor': 'xen',
    'ImageId': 'ami-a77c30c1',
    'InstanceId': 'i-035c9b0b8eb33988a',
    'InstanceType': 't2.micro',
    'KeyName': 'shivaq01Test',
    'LaunchTime': datetime.datetime(2018, 4, 1, 14, 10, 40, tzinfo=tzutc()),
    'Monitoring': {'State': 'disabled'},
    'NetworkInterfaces': [{'Association': {'IpOwnerId': 'amazon',
       'PublicDnsName': 'ec2-52-196-45-218.ap-northeast-1.compute.amazonaws.com',
       'PublicIp': '52.196.45.218'},
      'Attachment': {'AttachTime': datetime.datetime(2018, 4, 1, 14, 10, 40, tzinfo=tzutc()),
       'AttachmentId': 'eni-attach-f9ad8f13',
       'DeleteOnTermination': True,
       'DeviceIndex': 0,
       'Status': 'attached'},
      'Description': '',
      'Groups': [{'GroupId': 'sg-419eb438', 'GroupName': 'MyDMZ'}],
      'Ipv6Addresses': [],
      'MacAddress': '06:99:6a:63:16:78',
      'NetworkInterfaceId': 'eni-5fcb6b61',
      'OwnerId': '136442786451',
      'PrivateDnsName': 'ip-172-31-46-181.ap-northeast-1.compute.internal',
      'PrivateIpAddress': '172.31.46.181',
      'PrivateIpAddresses': [{'Association': {'IpOwnerId': 'amazon',
         'PublicDnsName': 'ec2-52-196-45-218.ap-northeast-1.compute.amazonaws.com',
         'PublicIp': '52.196.45.218'},
        'Primary': True,
        'PrivateDnsName': 'ip-172-31-46-181.ap-northeast-1.compute.internal',
        'PrivateIpAddress': '172.31.46.181'}],
      'SourceDestCheck': True,
      'Status': 'in-use',
      'SubnetId': 'subnet-0d792044',
      'VpcId': 'vpc-31038256'}],
    'Placement': {'AvailabilityZone': 'ap-northeast-1a',
     'GroupName': '',
     'Tenancy': 'default'},
    'PrivateDnsName': 'ip-172-31-46-181.ap-northeast-1.compute.internal',
    'PrivateIpAddress': '172.31.46.181',
    'ProductCodes': [],
    'PublicDnsName': 'ec2-52-196-45-218.ap-northeast-1.compute.amazonaws.com',
    'PublicIpAddress': '52.196.45.218',
    'RootDeviceName': '/dev/xvda',
    'RootDeviceType': 'ebs',
    'SecurityGroups': [{'GroupId': 'sg-419eb438', 'GroupName': 'MyDMZ'}],
    'SourceDestCheck': True,
    'State': {'Code': 16, 'Name': 'running'},
    'StateTransitionReason': '',
    'SubnetId': 'subnet-0d792044',
    'Tags': [{'Key': 'Base-Retention-days', 'Value': '1'},
     {'Key': 'Name', 'Value': 'ami-backup-test'}],
    'VirtualizationType': 'hvm',
    'VpcId': 'vpc-31038256'}],
  'OwnerId': '136442786451',
  'ReservationId': 'r-05a8e892129509682'}]


top レベルキー
# for key1 in response:print(key1)
# Reservations
# ResponseMetadata

Reservations レベルキー
# for i in response["Reservations"]:
#     for key2 in i:print(key2)
# Groups
# Instances
# OwnerId
# ReservationId

Instances レベルキー
# for i in response["Reservations"]:
#     for j in i["Instances"]:
#         for key3 in j:print(key3)
# AmiLaunchIndex
# ImageId
# InstanceId
# InstanceType
# KeyName
# LaunchTime
# Monitoring
# Placement
# PrivateDnsName
# PrivateIpAddress
# ProductCodes
# PublicDnsName
# PublicIpAddress
# State
# StateTransitionReason
# SubnetId
# VpcId
# Architecture
# BlockDeviceMappings
# ClientToken
# EbsOptimized
# EnaSupport
# Hypervisor
# NetworkInterfaces
# RootDeviceName
# RootDeviceType
# SecurityGroups
# SourceDestCheck
# Tags
# VirtualizationType
