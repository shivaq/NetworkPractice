▼ CFN-Init
-------------------------------------------------
  // - desired-state エンジン。手続き的ではない
  // - cfn-init プロセスにディレクティブを渡す
  - OS インディペンデント であろうとする
  // - 順番指定がいい感じ
  // - タイミングの管理ができる
  // - S3 などのエンドポイントに対する認証も使える

  - 冪等性を保証
  // ・Amazon AMI Linux の場合 →4 つのpython スクリプトを使う
  ・Amazon AMI Linux 以外 →yum を使ってインストール
  // ・Linux システムのすべてのメタデータの種類をサポート
  cfn-init を使用して既存のファイルを更新すると、.bak ファイルが生成される

  ▼ UserData と CfnInit との違い
  // ・UserData
  //   - procedural steps A ->B ->C
  //   - 管理が面倒
  //   - OS や Distribution を指定して行う
-------------------------------------------------

ログ格納先
-------------------------------------------------
  ▼ /var/log/cloud-init.log
  // UserData ブロックの実行結果を出力
  ▼ /var/log/cfn-init-log
  // /var/log/cfn-init-log を見ると、commands の各ステップの完了状況が出力されている
  // CloudFormation::Init プロセスの出力
-------------------------------------------------





▼ CFN-Init を使う前に、CFN のヘルパースクリプトをアップデート
-------------------------------------------------
UserData:
  "Fn::Base64":
    // Sub:スタックを作成/更新するまで使用できない値を含む
    // コマンド/出力を作成するために、この関数を使用
    // ここでは、UserData の Base64 が走る前に、パラメータから取得する各種 値を
    // 下記コマンドに渡した上で、Base64 を走らせる
    // パイプは、続く行がマルチラインになることを表す
    !Sub |
      //!/bin/bash

      // aws-cfn-bootstrap →CloudFormation ヘルパースクリプト が含まれたパッケージ
      // ヘルパースクリプト →cfn-signal, hup, init, get-metadata... などなど
      yum update -y aws-cfn-bootstrap // CFN-Init 系を行う時は、まずこれを実行するのがベストプラクティス
      // configsets "wordpress" を指定。指定しない場合、デフォルトのコンフィグ名 "config" が走る
      /opt/aws/bin/cfn-init -v --stack ${AWS::StackName} --resource MyEC2 --configsets wordpress --region ${AWS::Region}
      yum -y update // cfn-init がインストールした諸々パッケージもアップデートされるので、ベストプラクティス
-------------------------------------------------













一般的なフロー
-------------------------------------------------
cfn-init
// 設定を立ち上げる
 →cfn-signal
 // 初期設定の完了を伝える
  →オプションとして cfn-hup
-------------------------------------------------


CreationPolicy と一緒に使うといい
-------------------------------------------------
  // CreationPolicy:
  //   ResourceSignal:
  //     Timeout: PT5M
-------------------------------------------------


▼ Packages
・プロセス順 rpm, yum/apt, rubygems, python

▼ groups:
-------------------------------------------------
// Linux/UNIX グループを作成して、グループ ID を割り当てる
// ID は何でもいい場合{}
 // group1: {}
 // group2:
 //  gid: "45"
-------------------------------------------------

▼ users:
-------------------------------------------------
// Linux/UNIX ユーザーを EC2 インスタンス上に作成

 // myUser:
 //  groups:
 //   - "group1"
 //   - "group2"
 //  uid: "50"
 //  homeDir: "/tmp"
-------------------------------------------------


sources:
-------------------------------------------------
  // zip や tar を ダウンロードして解凍
  ・ブートストラップ用の スクリプトを S3 とかに格納しておいて、解凍 →実行すると便利
  // ・Github プロジェクトを DL するとか
  // /etc/myapp: "https://s3.amazonaws.com/mybucket/myapp.tar.gz"
    // "/home/ec2-user/aws-cli": "https://github.com/aws/aws-cli/tarball/master"
-------------------------------------------------


services
// インスタンスが起動されるときに有効化または無効化する必要のあるサービスを定義
// ソース、パッケージ、ファイルへの依存関係も指定でき、インストールされているファイルのために再起動が必要になった場合に、cfn-init がサービスの再起動を処理
-------------------------------------------------
services:
  sysvinit:
    nginx:
      // enabled: "true"  // 起動時にサービスON
      // ensureRunning: "true"// このプロセスが終わった後で走っている状態を保証
      // files:
      //   - "/etc/nginx/nginx.conf"
      // sources:
      //   - "/var/www/html"
    sendmail:
      enabled: "false"
      ensureRunning: "false"

  windows:
    cfn-hup:
      // enabled: "true"
      // ensureRunning: "true"
      // files:
      //   - "c:\\cfn\\cfn-hup.conf"
      //   - "c:\\cfn\\hooks.d\\cfn-auto-reloader.conf"
-------------------------------------------------

commands

  - test →command キーに指定されているコマンドが cfn-init で実行されるかどうかを決定するテストコマンド。テストが成功すると、cloud-init はコマンドを実行
  // - ignoreErrors →コマンドが失敗した 場合に cfn-init を実行し続けるかどうかを指定するブール値
  // - waitAfterCompletion →Windows システムの場合のみ。コマンドによって再起動が行われる場合に、コマンド終了後にどのぐらい待機するかを指定

  ※※ コマンドとコマンドとの間に、60 秒の待ち時間がデフォルトで設定されている
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






▼ cfn-get-metadata
-------------------------------------------------
CloudFormation からメタデータブロックを取得して、標準出力に出力
・対象リソースの全メタデータ
・特定のキーへのパス
・メタデータのサブツリー
-------------------------------------------------
















▼ cfn-hup
-------------------------------------------------
スタックの変更を検知する。
スタック変化時に起こすアクション例
・files のファイルが参照しているパラメータの変化を反映
・サービスの再起動
・conf ファイルをバックアップするシェルを走らせる
・ansible のコマンドを走らせる
-------------------------------------------------
・hooks.conf に、デーモンが定期的に呼び出すユーザーアクションを定義
・hooks.conf は cfn-hup デーモンの開始時だけにロードされる。
・新しいフックではデーモンを再起動する必要あり


// CFN をセットするためのブロックを解説していきます
configure_cfn:
  files:
▼ cfn-auto-reloader.conf
-------------------------------------------------
    /etc/cfn/hooks.d/cfn-auto-reloader.conf:
      triggers=post.update →スタックがアップデートされたら、
      // action= →このアクションを実行 →cfn-init の このスタックこのリソースこのconfigsets...
      content: !Sub |
        [cfn-auto-reloader-hook]
        triggers=post.update
        path=Resources.MyEC2.Metadata.AWS::CloudFormation::Init
        action=/opt/aws/bin/cfn-init -v --stack ${AWS::StackName} --resource MyEC2 --configsets wordpress --region ${AWS::Region}
      mode: "000400"
      owner: root
      group: root
-------------------------------------------------


▼ cfn-hup.conf
-------------------------------------------------
cfn-hup →リソースメタデータの変更を検出し、指定された操作を実行するデーモン
cfn-hup.log に個々で定義したスタック、リージョンの内容が出力される
  // hooks.d フォルダ内の content に基いて、
  // スタック情報を、5分毎にポーリング。
    // [cfn-auto-reloader-hook]
    // の path をチェックして、
    // triggers=post.update をトリガーに、action が実行される
-------------------------------------------------
    /etc/cfn/cfn-hup.conf:
      content: !Sub |
        [main]
        stack=${AWS::StackId}
        region=${AWS::Region}
        verbose=true
        interval=5
      mode: "000400"
      owner: root
      group: root
-------------------------------------------------


▼ cfn-hup プロセス再起動を定義
-------------------------------------------------
  services:
    sysvinit:
      cfn-hup:
        enabled: "true"
        ensureRunning: "true"
        // 下記ファイルに変更がなされると、cfn-hup が再起動する
        files:
          - "/etc/cfn/cfn-hup.conf"
          - "/etc/cfn/hooks.d/cfn-auto-reloader.conf"
-------------------------------------------------


▼ ログ格納先
-------------------------------------------------
  ▼ /var/log/cfn-hup.log
// Cfn-Hup プロセスの出力
// スタック情報変化チェック結果とかが出力される。
-------------------------------------------------






▼ hooks.conf
-------------------------------------------------
[hookname]
// 検出する条件のリスト
triggers=post.add or post.update or post.remove
// リソースの最終更新時間を監視し、変更されたらトリガー
path=Resources.<logicalResourceId>
// 関連するリソースIDが変化した場合だけトリガー
path=Resources.<logicalResourceId> .PhysicalResourceId
// メタデータの変更を関し
path=Resources.<logicalResourceId> .Metadata.<optionalMetadatapath>)
action=<arbitrary shell command>
// cfn-hupは、ユーザー切替に su を使用
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





▼ cfn-signal
-------------------------------------------------
サービスが、いつ準備完了状態になったかを伝えるのに有用

CFN が、 CREATE_COMPLETE とするまでシグナルを待つようにさせることが可能。
つまり、次のステップに進む条件をしてすることになる。

いつ使うのか：
UserData、cfn-init や、ユーザー側のイベントの実行を待つ。
で、
cfn-signal を開始/再開のトリガーとする。
-------------------------------------------------



▼ Creation Policy
-------------------------------------------------
シグナルを受けて、CREATE_COMPLETE にするかどうかの制御をカスタマイズする
-------------------------------------------------
CREATE_IN_PROGRESS
// リソース間の依存関係を計算する。
// 他のAWSサービスと交信して、サービスを作成
-------------------------------------------------













6：37 まで

▼ cfn-init の成否を、CREATE_COMPLETE に遷移するかどうかに反映させる方法
-------------------------------------------------
cfn-init の成否を見届けてから、cfn-signal を CreationPolicy に送り、
CREATE_COMPLETE に状態を遷移するかどうかを判断するスニペット
-------------------------------------------------
EC2:
  Type: "AWS::EC2::Instance"
  Properties:
    ...
    UserData:
    "Fn::Base64":
      !Sub |
        #!/bin/bash

        // 前提条件
        // そもそも、この UserData セクションは、!Sub とともに記述されているため、
        // このスタックが一通り完了した後で走る。


        // コード1: 下記 cfn-init のコンフィグセットの実行を行う
        /opt/aws/bin/cfn-init -v --stack ${AWS::StackName} --resource EC2 --configsets wordpress --region ${AWS::Region}

        // コード2: スタックに戻って、c、
        //$? →直前のコマンドの返り値（0 が成功。失敗はプログラムによって値が変わる。-1 が多い）
        // cfn-signal から、cfn-init コマンドの成否の返り値、スタック名、リソース、リージョンといった情報を、
        /opt/aws/bin/cfn-signal -e $? --stack ${AWS::StackName} --resource EC2 --region ${AWS::Region}
        // 下記 CreationPolicy > ResourceSignal ブロックに渡す。
        // その結果により、CREATE_IN_PROGRESS から 成功または失敗ステートに遷移していく
  CreationPolicy:
    ResourceSignal:
    // もしも cfn-signal が UserData プロパティ内になかったら、
    // UserData の処理が成功しても、CREATE_COMPLETE に遷移せず、CFn がTimeoutまでハングする
    // ※ cfn-init-log 及び cloud-init-log ファイルを見れば、プロセスの結果がわかります
      Count: "1"// CFNが CREATE_COMPLETE と認識するのに必要な、受信するシグナルの数。
      // AutoScaling の時などは、一つじゃ足りない
      Timeout: PT15M // リソースからのシグナルを待つ時間(分)。成功か失敗か。時間が過ぎたら失敗とする。
-------------------------------------------------
・AutoScaling グループ内に CreationPolicy を定義する場合、
Count: 1 ではなく、オートスケーリング時に必要なインスタンス数を指定する。
-------------------------------------------------


金たん 4200
もも 2400
肉まん 3450
花 8950
