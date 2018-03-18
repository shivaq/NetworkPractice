
▼ デバッグ
-------------------------------------------------
・まずは、小さなスタンドアローンな Stack を作って、ユニットテスト的なことをしましょう。
・リソースタイプによって、作成/削除 時間が異なる事を知りましょう。
// AWS::CloudFront::Distribution →30-60min
// AWS::EC2::SecurityGroup → 数秒
・一部のエラーが、全体のスタックやり直しにつながる
・インクリメンタルな更新という形でスタックを大きくしましょう
一度に1 つか 2,3 のリソースを追加 →失敗しても、ロールバックされる
・イベント をみて、スタック作成/更新 の進捗をチェックしましょう

・EC2 にログインして、 /var/log/boot.log を tail しましょう
-------------------------------------------------
デザイナーで検証
Yaml のフォーマットチェック。
// aws cloudformation validate-template
// Key のタイポとかはチェックしてくれない
-------------------------------------------------

▼ troposphere
-------------------------------------------------
テンプレートを抽象化して作成。
YAML 化の段階で検証してくれる。
-------------------------------------------------


▼ テンプレートオプション
-------------------------------------------------
失敗時のロールバック を しないを選択するケース
失敗した理由をデバッグしたい場合
-------------------------------------------------








▼ 基本
-------------------------------------------------
テンプレートファイルの取り扱われ方
// テンプレートを選択すると、まずそれは自動生成された S3 バケツにアップロードされ、
// そのファイルが CF から参照される。

スタックの更新時に起きていること
// パラメータ等はそのままだが、元ファイル(リソース？)については、編集をするというより、
// アップロード前のテンプレートファイルを編集し、再アップロードして、
// AWS は前のバージョンとの変更点を検出し、それに応じてコンポートの削除や作成を行う
更新に伴う要件
更新プロパティによって異なる。アクセスの中断の有無 及び 置換

置換 →旧リソースは一旦削除される もしくは Deletion Policy によって削除できない
// バケツ名更新時、置換が行われるその際、古いバケツはそのまま削除 →新しい名前の空のバケツができる

スタック名
// スタックは名前で同定される。
// 名前はユニークでなければならない
// 更新時にスタック名を編集できない

テンプレートは 基本的に、宣言されなければならない。
// 動的にリソースを生成させることはできない

一部サポートされない AWS サービスが有る。回避策もある。
// Lambda を使えばなんとかなる
-------------------------------------------------





▼ Amazon CLI を使って CloudFormation
-------------------------------------------------
名前をつけてプロファイル(リージョンやパスワードなど)を設定
// aws configure --profile my_defined_user
Amazon CLI を使って CloudFormation     各ファイルがあるディレクトリ上で実行
// aws cloudformation create-stack --stack-name cli-stack-sample-name --template-body file://templateFromCLI.yaml --parameters file://parameters.json --profile cf-course --region us-east-1
スタックを作成
// create-stack
スタックの名前を定義
// --stack-name cli-stack-sample-name
テンプレートファイルを参照
// --template-body file://templateFromCLI.yaml
パラメータファイルは json のみサポート
// --parameters file://parameters.json
名指しでプロファイルデータを選択
// --profile my_defined_user
リージョンを指定することもできる
// --region us-east-1

// # some options:
// # [--disable-rollback | --no-disable-rollback]
// # [--rollback-configuration <value>]
// # [--timeout-in-minutes <value>]
// # [--notification-arns <value>]
// # [--capabilities <value>]
// # [--resource-types <value>]
// # [--role-arn <value>]
// # [--on-failure <value>]
// # [--stack-policy-body <value>]
// # [--stack-policy-url <value>]
// # [--tags <value>]
// # [--client-request-token <value>]
// # [--enable-termination-protection | --no-enable-termination-protection]
// # [--cli-input-json <value>]
// # [--generate-cli-skeleton <value>]

-------------------------------------------------




▼ 書き方
-------------------------------------------------
YAML ファイルに記載した順番に関係なく、
CloudFormation は正しい順番で各コンポーネントを生成していく
// SecurityGroup -> EC2 instance -> EIP

key/value の value が空の場合は {}
-------------------------------------------------









▼ Resources
-------------------------------------------------
AWS コンポーネント

フォーマット
// AWS::サービス名:: データタイプ名


DependsOn:
// 二つのリソース間の依存関係を表現
例
// ASG を作成した後に、ECS クラスタを作成する

DeletionPolicy:
// CloudFormation が削除されても、RDS は削除させないとか
-------------------------------------------------


▼ DeletionPolicy
-------------------------------------------------
・Delete
// リソースとコンテンツを全部削除
// S3 には適用されない

・Retain
// スタックが削除されても、リソースもコンテンツも削除されない。

・Snapshot
// snapshot をサポートするリソースにのみ対応
// EC2 ボリューム、ElasticCache RDS Redshift など
// 削除前にスナップショットを作成する
-------------------------------------------------




▼ Parameter
-------------------------------------------------
要入力項目、デフォルト値、リスト、制約をセットできる
// コンソール上でスタックを作成または更新する際、
// デフォルト値や、デフォルトリスト、制約を設定できる。
//
// どんな場合に使うか？
// 規定の範囲で何度も作成する場合。
-------------------------------------------------


▼ Parameter vs Mappings
-------------------------------------------------
Parameter →更新することで可変可能な値
Mappings →事前に決まっている値。不可変
-------------------------------------------------







▼ Mappings
-------------------------------------------------
enumみたく使える
// dev vs prod, Region, AZ, AWS アカウント, AMI タイプ

Map した値の参照
// !FindInMap [マップ名、トップレベルKey, セカンドレベルKey]
// InstanceType: !FindInMap [EnvironmentToInstanceType, !Ref 'EnvironmentName', instanceType]
-------------------------------------------------






▼ Outputs
-------------------------------------------------
他のスタックが 参照 できる アウトプットを宣言

スタックの 出力欄に Key/Value という形で表示される

他のスタックから依存されている限り、スタックを削除することはできない
// イベント欄で、削除できなかったと表示される

ネットワークをデプロイする際に、
// VPC ID や サブネット ID をアウトプットするなど

スタックをを超えて、コラボレーションを行う際に。
// ネットワークの専門家と、アプリケーションの専門家とのコラボなど。
-------------------------------------------------


▼ Conditions
-------------------------------------------------
制約は、他の制約や値、マップを参照できる

よく使われるConditions
// 環境 dev, test, prod
// リージョン
// パラメータ
-------------------------------------------------



▼ Metadata
-------------------------------------------------
CloudFormation で、そのスタックにパラメータ入力する際の UI をわかりやすくする
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
// DBSnapshotIdentifier=UseDBSnapshot?DBSnapshotName:プロパティ自体削除
// DBSnapshotIdentifier:
// Fn::If:
// - UseDBSnapshot
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








▼ CFN-Init
-------------------------------------------------
CF のヘルパースクリプト
// ・Amazon AMI Linux の場合 →4 つのpython スクリプトを使う
// ・Amazon AMI Linux 以外 →yum を使ってインストール

一般的なフロー
cfn-init
// 設定を立ち上げる
 →cfn-signal
 // 初期設定の完了を伝える
  →オプションとして cfn-hup

CreationPolicy と一緒に使うといい
  // CreationPolicy:
  //   ResourceSignal:
  //     Timeout: PT5M

ログ格納先
ブートストラップ
// /var/log/cloud-init-output.log
cfn-init
// /var/log/cfn-init.log

-------------------------------------------------


▼ cfn-init
-------------------------------------------------
// ・Linux システムのすべてのメタデータの種類をサポート
AWS::CloudFormation::Init キーからテンプレートメタデータを読み取り、
以下の処理を行う
// ・AWS CloudFormation のメタデータの取得と解析
// ・パッケージのインストール
// ・ディスクへのファイルの書き込み
// ・サービスの有効化/無効化と開始/停止

// cfn-init を使用して既存のファイルを更新すると、.bak ファイルが生成される

Packages
・プロセス順 rpm, yum/apt, rubygems, python

groups:
// ID は何でもいい場合{}
 // group1: {}
 // group2:
 //  gid: "45"

users:
 // myUser:
 //  groups:
 //   - "group1"
 //   - "group2"
 //  uid: "50"
 //  homeDir: "/tmp"

zip や tar を ダウンロードして解凍
・common スクリプトを S3 とかに格納しておいて、解凍 →実行すると便利
・Github プロジェクトを DL するとか

  sources:
  // /etc/myapp: "https://s3.amazonaws.com/mybucket/myapp.tar.gz"
    // "/home/ec2-user/aws-cli": "https://github.com/aws/aws-cli/tarball/master"

-------------------------------------------------


▼ cfn-signal
-------------------------------------------------
各種シグナルを AWS CloudFormation に送信
・EC2 instance の作成/更新成否を示すシグナル
・アプリインストールできる状態になった事を示すシグナル

・下記作成ポリシーを使う場合、一定条件整うまでスタック作業停止
作成ポリシー
// CreationPolicy または WaitOnResourceSignals 更新ポリシーを持つ Auto Scaling グループ
作業再開条件
// 必要な数のシグナルを受信 または タイムアウト期間超過
-------------------------------------------------



▼ cfn-get-metadata
-------------------------------------------------
CloudFormation からメタデータブロックを取得して、標準出力に出力
・対象リソースの全メタデータ
・特定のキーへのパス
・メタデータのサブツリー
-------------------------------------------------
▼ cfn-hup
-------------------------------------------------
リソースメタデータの変更を検出し、指定された操作を実行するデーモン
・hooks.conf に、デーモンが定期的に呼び出すユーザーアクションを定義
・hooks.conf は cfn-hup デーモンの開始時だけにロードされる。
・新しいフックではデーモンを再起動する必要あり

▼ hooks.conf
-------------------------------------------------
[hookname]
# 検出する条件のリスト
triggers=post.add or post.update or post.remove
# リソースの最終更新時間を監視し、変更されたらトリガー
path=Resources.<logicalResourceId>
# 関連するリソースIDが変化した場合だけトリガー
path=Resources.<logicalResourceId> .PhysicalResourceId
# メタデータの変更を関し
path=Resources.<logicalResourceId> .Metadata.<optionalMetadatapath>)
action=<arbitrary shell command>
# cfn-hupは、ユーザー切替に su を使用
runas=<runas user>
-------------------------------------------------
▼ hooks.d ディレクトリ
-------------------------------------------------
・フック設定ファイルを格納
・hooks.conf ファイルと同じレイアウト
・hooks.conf 内のフックと同じ名前のフックがある場合、hooks.d が hooks.conf を上書き
// ...
//   LaunchConfig:
//     Type: "AWS::AutoScaling::LaunchConfiguration"
//     Metadata:
//       QBVersion: !Ref paramQBVersion
//       AWS::CloudFormation::Init:
// ...
// /etc/cfn/hooks.d/cfn-auto-reloader.conf:
//   content: !Sub |
//     [cfn-auto-reloader-hook]
//     triggers=post.update
//     path=Resources.LaunchConfig.Metadata.AWS::CloudFormation::Init
//     action=/opt/aws/bin/cfn-init -v --stack ${AWS::StackName} --resource LaunchConfig --configsets wordpress_install --region ${AWS::Region}
//     runas=root
//   mode: "000400"
//   owner: "root"
//   group: "root"
-------------------------------------------------



▼ EC2-user data
-------------------------------------------------
EC2 作成時の、ブートストラップの bash のこと
・スクリプトが長くなると、管理しづらい
・EC2 を terminate せずに更新したい場合に困る
・可読性が低い
・スクリプトの完了成否がわからない

// Type: AWS::EC2::Instance
// Properties:
//   ..
  // UserData:
  //   Fn::Base64: |
  //      #!/bin/bash
  //      yum update -y
-------------------------------------------------







■■ ベストプラクティス ■■

▼ レイヤード vs サービス指向 アーキテクチャー の違い
組織の方針によって分かれる
-------------------------------------------------
Layered architecture// horizontal layers
ネットワークレイヤー
インスタンスレイヤー
アプリケーションレイヤー
と分けて構築している
-------------------------------------------------
service oriented architecture// vertical layer
すべての CloudFormation が、
1 つのサービスのネットワークやアプリケーションを含んでいる
-------------------------------------------------


▼ クロススタックリファレンスを使いましょう
-------------------------------------------------
ネットワークスタックから、
VPC や サブネットを 参照する
再利用が容易になる
-------------------------------------------------

▼ 各環境と密に結合したテンプレートにならないようにしましょう
-------------------------------------------------
dev/test/prod
リージョン や アカウント が違っても利用できるように。
-------------------------------------------------



▼ CloudFormation に credential を組み込まないようにしましょう
-------------------------------------------------
NoEcho パラメータや KMSを を使いましょう
-------------------------------------------------


▼ パラメータには、常に type や constraints を使えないか試しましょう


▼ EC2 インスタンスを使う時は、必ず CFN-Init を使いましょう

▼ テンプレートを実行する前に、必ず validate しましょう

▼ スタックの要素に対して手動で何かをしたりしてはいけません
state が mismatch になりかねません。
必ず CloudFormation 経由で行いましょう


▼ 変更は changesets を使って verify しましょうそうすれば、災害可能性が低くなる
https://docs.aws.amazon.com/ja_jp/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-changesets.html


▼ Deletion ポリシーを必ず使いましょう
-------------------------------------------------
