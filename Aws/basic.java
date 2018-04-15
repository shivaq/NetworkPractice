▼ 使えそうな情報
-------------------------------------------------
▼ インスタンスのシステムログを見る
EC2 インスタンスの、コンソールのアウトプットをすべて見ることができる
EC2 インスタンス > アクション > インスタンスの設定 > システムログの取得


-------------------------------------------------

DevelpperAssociate は SoftwareArchitectAssociate を取得できていれば、
1,2時間の追加勉強で取得できる

▼ CommandLineTool を有効化する方法// Amazon CLI
-------------------------------------------------
準備
AccessKeyId と SecretAccessKey を用意
// ユーザー生成時の SecretAccessKey を保存していなかったら、
// 改めて生成するか、新規ユーザーを作ってそいつのを使う

ダウンロード →[Git,コマンドプロンプト]を開く
下記押下すると、AccessKeyId と SecretAccessKey を要求される
// aws configure
東京 Region は下記の通り。違うの入れると、Invalid IPv6 とか出てアクセスできない
// ap-northeast-1
Default output format は 空のまま Enter でいい。

下記で表示されれば成功
// aws s3 ls

各プロファイルの情報は下記にある
// $HOME/.aws/config, credentials

プロファイル名を切り替えて登録
// aws configure --profile new_profile_name

プロファイル名を指定して aws コマンド実行
// aws s3 ls --profile backupuser
-------------------------------------------------






▼ AMI のアドレスについて
-------------------------------------------------
ローカルから ssh する時に使える VM のアドレス
使える
パブリックDNS、パブリックIP
使えない
プライベートDNS、プライベートIP
-------------------------------------------------











▼ EC2 にファイルを転送
-------------------------------------------------
アップロード
// scp -i ansibleWS.pem soushinTaishou.txt ubuntu@ec2-13-231-60-165.ap-northeast-1.compute.amazonaws.com:~
// 説明 ⇒：scp(secure copy) -i pemファイル 送信したいファイル アカウント@アドレス:~/target/directory
ダウンロード
// scp -i pemのパス ec2-user@host:ダウンロード対象ファイルパス .
// scp -i ansibleWS.pem ubuntu@ec2-13-231-60-165.ap-northeast-1.compute.amazonaws.com:/home/ubuntu/playground/ansibleDocs.java ~/Dropbox/01.study/Linux/Playground/confFiles
-------------------------------------------------





SSH アクセス方法
-------------------------------------------------
EC2 作成時にダウンロードした Pem ファイルを、任意の場所に格納する
Git 等でそのディレクトリに移動
全アクセス権を読み込みのみに変更
// chmod 400 qqqqq.pem

SSH で VM にアクセス
ユーザー名： ec2-user// Amazon linux の場合
対象IPアドレス：EC2 の IPv4 パブリック IP
公開鍵が認証時に ID を読み込むファイルを -i で指定
// ssh ec2-user@54.199.247.174 -i qqqqq.pem
-------------------------------------------------




▼ AMI に パスワードを使って接続
-------------------------------------------------
ユーザーを追加
// sudo useradd -s /bin/bash -m -d /home/USERNAME  -g root USERNAME

パスワードを設定
// sudo passwd USERNAME

ルート権限を付与
// sudo visudo
// USERNAME  ALL=(ALL:ALL) ALL

SSH　パスワードを受け入れるように設定変更
// /etc/ssh/sshd_config
// PasswordAuthentication yes

ssh を再起動
// /etc/init.d/ssh restart

追加したユーザー名でSSH　接続
// ssh USERNAME@ipv4_address
-------------------------------------------------
