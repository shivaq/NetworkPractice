▼ EC2 インスタンス作成時に、bash を走らせる用仕込む
-------------------------------------------------
コンソール上で、 EC2 画面を開き、リージョンをアメリカ東部に変更

S3 バケツをアメリカ東部に作り、index.html をアップロード

IAM で EC2 用の S3 アドミンな ロールを作成

EC2 に上記割り当てて
-------------------------------------------------






▼ ブートストラップ スクリプト
-------------------------------------------------
インスタンスの詳細の設定 > 高度な詳細
# >ユーザーデータ + テキストで  の部分に bash スクリプトを書き込み

# Apache のインストール等しておけば、
# 該当 EC2 の パブリック IP アドを叩けば サイトが表示される


※ バケツのアドレス等は、コンソールの S3 のバケツ名をコピペ
もしくは、
CLI に入っているならば、下記で表示される
# aws s3 ls

s3 バケツからのコピースクリプトは、予め間違いないかチェックしておいたほうがいいかも。

ブートストラップ bash 例
-------------------------------------------------
#!/bin/bash
yum update -y
yum install httpd -y
service httpd start
chkconfig httpd on
aws s3 cp s3://qqqqq-test /var/www/html/ --recursive
-------------------------------------------------



メタデータを利用する
-------------------------------------------------
EC2 にログインして、下記コマンドでその AMI のメタデータが取得できる
# curl http://169.254.169.254/latest/meta-data/
# ami-id
# ami-launch-index
# ami-manifest-path
# block-device-mapping/
# hostname
# iam/
# instance-action
# instance-id
# instance-type
# local-hostname
# local-ipv4
# mac
# metrics/
# network/
# placement/
# profile
# public-hostname
# public-ipv4
# public-keys/
# reservation-id
# security-groups

上記情報と、インスタンス作成時 bash を組み合わせて、
hostname を設定したりできる
