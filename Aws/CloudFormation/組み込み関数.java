

▼ Ref
-------------------------------------------------
パラメーターの論理名を指定 →パラメータの値を返す
リソースの論理名を指定 リソースを参照するために通常使用できる値を返す
AWS::EC2::EIP →IP アドレスを返す
AWS::EC2::Instance →インスタンス ID を返す

ドキュメントの 参照番号 に、
戻り値としての Ref がある
-------------------------------------------------


▼ !GetAtt
-------------------------------------------------
!GettAtt リソースのロジカル名.属性名
!GetAtt EC2Instance.AvailabilityZone
ドキュメントの Fn::GetAtt に、戻り値としての 属性名がある
-------------------------------------------------










▼ !Join
-------------------------------------------------
!Join [ delimiter, [ comma-delimited list of values ] ]

!Join [ ":", [ a, b, c ] ]

!Join
  - ''
  - - 'arn:'
    - !Ref Partition
    - ':s3:::elasticbeanstalk-*-'
    - !Ref 'AWS::AccountId'
-------------------------------------------------










▼ ！Select
-------------------------------------------------
オブジェクトのリストから 1 つのオブジェクトを返す

!Select [ index, listOfObjects ]

!Select [ "1", [ "apples", "grapes", "oranges", "mangoes" ] ]


// Parameters:
//   DbSubnetIpBlocks: 
//     Description: "Comma-delimited list of three CIDR blocks"
//     Type: CommaDelimitedList
//     Default: "10.0.48.0/24, 10.0.112.0/24, 10.0.176.0/24"

Subnet0:
  Type: "AWS::EC2::Subnet"
  Properties:
    VpcId: !Ref VPC
    CidrBlock: !Select [ 0, !Ref DbSubnetIpBlocks ]
-------------------------------------------------
AvailabilityZone: !Select
  - 0
  // !GetAZs !Ref のようなパターンは無効。短い形式の関数を連続してネストすることはできない
  - Fn::GetAZs: !Ref 'AWS::Region'
-------------------------------------------------




▼ Fn::GetAZs
-------------------------------------------------
指定されたリージョンのアベイラビリティーゾーンを含んだ配列を返す

!GetAZs region


-------------------------------------------------


▼ !SUB
-------------------------------------------------
!Ref や Pseudo 変数と組み合わせて使える
-------------------------------------------------
