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


Configset
// 複数の設定キーを作成し、特定の順序で cfn-init によって処理されるようにする
-------------------------------------------------
configSets:
  ascending:
    - "config1"
    - "config2"
  descending:
    - "config2"
    - "config1"
config1:
  commands:
    test:
      command: "echo \"$CFNTEST\" > test.txt"
      env:
        CFNTEST: "I come from config1."
      cwd: "~"
config2:
  commands:
    test:
      command: "echo \"$CFNTEST\" > test.txt"
      env:
        CFNTEST: "I come from config2"
      cwd: "~"
-------------------------------------------------

Packages
・プロセス順 rpm, yum/apt, rubygems, python

groups:
// Linux/UNIX グループを作成して、グループ ID を割り当てる
// ID は何でもいい場合{}
 // group1: {}
 // group2:
 //  gid: "45"

users:
// Linux/UNIX ユーザーを EC2 インスタンス上に作成

 // myUser:
 //  groups:
 //   - "group1"
 //   - "group2"
 //  uid: "50"
 //  homeDir: "/tmp"



  sources:
  // zip や tar を ダウンロードして解凍
  // ・ブートストラップ用の スクリプトを S3 とかに格納しておいて、解凍 →実行すると便利
  // ・Github プロジェクトを DL するとか
  // /etc/myapp: "https://s3.amazonaws.com/mybucket/myapp.tar.gz"
    // "/home/ec2-user/aws-cli": "https://github.com/aws/aws-cli/tarball/master"

services
// インスタンスが起動されるときに有効化または無効化する必要のあるサービスを定義
// ソース、パッケージ、ファイルへの依存関係も指定でき、インストールされているファイルのために再起動が必要になった場合に、cfn-init がサービスの再起動を処理
-------------------------------------------------
services:
  sysvinit:
    nginx:
      enabled: "true"
      ensureRunning: "true"
      files:
        - "/etc/nginx/nginx.conf"
      sources:
        - "/var/www/html"
    php-fastcgi:
      enabled: "true"
      ensureRunning: "true"
      packages:
        yum:
          - "php"
          - "spawn-fcgi"
    sendmail:
      enabled: "false"
      ensureRunning: "false"

  windows:
    cfn-hup:
      enabled: "true"
      ensureRunning: "true"
      files:
        - "c:\\cfn\\cfn-hup.conf"
        - "c:\\cfn\\hooks.d\\cfn-auto-reloader.conf"
-------------------------------------------------

commands

  // - test →command キーに指定されているコマンドが cfn-init で実行されるかどうかを決定するテストコマンドです。テストが成功すると、cloud-init はコマンドを実行
  // - ignoreErrors →コマンドが失敗した 場合に cfn-init を実行し続けるかどうかを指定するブール値
  // - waitAfterCompletion →Windows システムの場合のみ。コマンドによって再起動が行われる場合に、コマンド終了後にどのぐらい待機するかを指定

  // ※※ コマンドとコマンドとの間に、60 秒の待ち時間がデフォルトで設定されている
  //      Windows で、コマンドが多い場合はめちゃくちゃ時間かかるので要調整
-------------------------------------------------
    commands:
      test:
        command: "echo \"$MAGIC\" > test.txt"
        env://  env →コマンドの環境変数を設定。既存の環境に追加するのではなく、既存の環境を上書き
          MAGIC: "I come from the environment!"
        cwd: "~" // - cwd →作業ディレクトリ
        test: "test ! -e ~/test.txt"// テストが成功すると、cloud-init はコマンドを実行
        ignoreErrors: "false"
      test2:
        command: "echo \"$MAGIC2\" > test2.txt"
        env:
          MAGIC2: "I come from the environment!"
        cwd: "~"
        test: "test ! -e ~/test2.txt"
        ignoreErrors: "false"
-------------------------------------------------

files
// EC2 インスタンス上にファイルを作成できます。内容は、テンプレートにおいてインラインで指定することも、URL から取得することもできます。
-------------------------------------------------
files:
  /tmp/setup.mysql:
    content: !Sub |
      CREATE DATABASE ${DBName};
      CREATE USER '${DBUsername}'@'localhost' IDENTIFIED BY '${DBPassword}';
      GRANT ALL ON ${DBName}.* TO '${DBUsername}'@'localhost';
      FLUSH PRIVILEGES;
    mode: "000644"
    owner: "root"
    group: "root"
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
