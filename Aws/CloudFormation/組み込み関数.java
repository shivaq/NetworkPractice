▼ Ref
-------------------------------------------------
パラメーターの論理名を指定 →パラメータの値を返す
リソースの論理名を指定 リソースを参照するために通常使用できる値を返す
AWS::EC2::EIP →IP アドレスを返す
AWS::EC2::Instance →インスタンス ID を返す

ドキュメントの 参照番号 に、
戻り値としての Ref がある
-------------------------------------------------



▼ Pseudo Parameters
-------------------------------------------------
Ansible の Magic 変数みたいなもの。ユーザーが定義できるものではない。

AWS::AccountId
// 返り値:スタックが作成されているアカウントの AWS アカウント ID

AWS::NotificationARNs
// 返り値:現在のスタックの通知 Amazon リソースネーム (ARN) のリスト
// - Ref: AWS::NotificationARNs
AWS::NoValue
// 返り値:Fn::If と組み合わせて、対応するリソースプロパティを削除

// DBSnapshotIdentifier:
// Fn::If:
// - UseDBSnapshot   booleanってこと？
// - Ref: DBSnapshotName   true の場合、この値
// - Ref: AWS::NoValue
AWS::Partition
// 返り値:リソースがあるパーティション。リージョンごとに異なる。aws-cn
AWS::Region
// 返り値:CloudFormation を作った AWS リージョンを表す文字列
AWS::StackId
// 返り値:スタックの ID
AWS::StackName
// 返り値:スタックの名前
AWS::URLSuffix
// 返り値:リージョン別ドメインのサフィックス
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
スタックを作成/更新するまで使用できない値を含む
コマンド/出力を作成するために、この関数を使用

!Ref や Pseudo 変数と組み合わせて使える
-------------------------------------------------
