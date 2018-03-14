バージニアにリージョン変更

S3 を扱える EC2 用ロールを作成
// S3-Admin-Access

VPC 用のセキュリティグループ作成
// VPC > セキュリティグループ
// WebDMZ
// RDS-SG

VPC 用のセキュリティグループ編集
// WebDMZ ⇒SSH, HTTP, 0.0.0.0/0
// RDS-SG ⇒MySQL/Aurora   WebDMZ

EC2
Application ロードバランサー作成
// My-ALB
// AZ を全部選択
// セキュリティグループ は デフォルト
// ターゲット名 ⇒ MyWebServers
// ヘルスチェックパスをセット ⇒ /healthy.html
// 確認 ⇒作成

S3 でバケツ作成
// qqqqq-wordpress-code
// qqqqq-wordpress-media

CloudFront をセットアップ
// Web ディストリビューション
// オリジナルドメイン名 ⇒qqqqq-wordpress-media
// パスは空
// Restrict Bucket Access ⇒YES
// Origin Access Identity ⇒Create a New Identity
// S3 にアクセスする際の、CloudFront ユーザーを新規作成する
// Grant Read Permissions on Bucket ⇒YES
// 作成した Origin Access Identity に、S3 バケツ内の OBJ へのアクセス権を付与
 // ⇒Create Distribution


RDS
// MySQL ⇒本番稼働用(有料) 時間あたり 0.034 USD
//  ⇒インスタンスタイプが T2 マイクロであることを確認
//  マルチ AZ 配置 ⇒YES
// DB インスタンス識別子、ユーザー名、パスワード全部一緒でいい
// 次へ
// セキュリティグループ ⇒RDS-SG
// DB 名 ⇒DB インスタンス識別子 と一緒でいい
//  ⇒作成

Route53
// Create Record Set ⇒Alias:Yes ⇒ターゲット:作成した ELB を選択
//  ⇒作成

EC2
IAM ロール ⇒S3-Admin-Access
ブートストラップは以下
// #!/bin/bash
// yum update -y
// yum install httpd php php-mysql stress -y
// cd /etc/httpd/conf
// cp httpd.conf httpdconfbackup.conf
// rm -rf httpd.conf
// wget https://s3-eu-west-1.amazonaws.com/acloudguru-wp/httpd.conf
// cd /var/www/html
// echo "healthy" > healthy.html
// wget https://wordpress.org/latest.tar.gz
// tar -xzf latest.tar.gz
// cp -r wordpress/* /var/www/html/
// rm -rf wordpress
// rm -rf latest.tar.gz
// chmod -R 755 wp-content
// chown -R apache:apache wp-content
// service httpd start
// chkconfig httpd on

// stress ⇒EC2 インスタンスにストレステストを行うやつ

/etc/httpd/conf の修正内容
-------------------------------------------------
アパッチが URL を rewirte できるようにする
なぜか
// WordPress のサイトに行くたびに、画像を見たらそれを CloudFront に保存して、
// S3 には保存しない。
// で、ユーザーが CloudFront を参照するように強いるため

// #
// # AllowOverride controls what directives may be placed in .htaccess files.
// # It can be "All", "None", or any combination of the keywords:
// #   Options FileInfo AuthConfig Limit
// #
    AllowOverride All
-------------------------------------------------

下記をする理由
-------------------------------------------------
cp -r wordpress/* /var/www/html/
*/
これをやらないと、ドメインネームの後に /wordpress を付け加えないといかんくなる

-------------------------------------------------

// タグ ⇒MyEC2WebServer
// SG ⇒WebDMZ
//  ⇒作成

EC2 ＞ ターゲットグループ // ELB のターゲット
ヘルスチェックを編集
// Healthy threshold: 2
// Interval: 6

ターゲットを編集
// MyEC2WebServer ⇒登録済みに追加

ロードバランサーのセキュリティグループを変更
 // ⇒WebDMZ
ドメインネームを叩いて、WordPress が表示されることを確認

ワードプレスを設定
// Let's go
// DB 名、ユーザー名、PW は RDS に MySQL インスタンスを作成した時のもの

MySQL のインストールされたエンドポイントを取得
// RDS > インスタンス選択
// エンドポイント + ポート番号 をコピー
// qqqqq777.c04dksdmmgkd.us-east-1.rds.amazonaws.com:3306
// ワードプレスの Database Host 欄に記載 ⇒submit

表示される php ファイルの内容を、
作成済み MyEC2WebServer EC2 インスタンスにファイルを作成してコピペ
// MyEC2WebServer の下記ファイルを作成してコピペ
// /var/www/html/wp-config.php

ブラウザ上:WordPress  ⇒Try install
// サイトタイトル、ユーザーネーム、PW などなど入れて、
// インストール WordPress
 // ⇒できあがり
アドミンでのログインは、
ドメイン名/wp-admin/

WordPress のコードを S3 にバックアップ
// バケツ名を確認
// aws s3 ls
再帰的にコピー
// aws s3 cp --recursive /var/www/html s3://qqqqq-wordpress-code
コピーできたことを確認
// aws s3 ls s3://qqqqq-wordpress-code

ワードプレスに画像をアップロードすると、EC2 に画像が格納されている事がわかる
// ワードプレスのアドミン > Media AddNew 画像をアップロード
// terminal からアップロードされたことを確認
// /var/www/html/wp-content/uploads/2018/03 みたいなとこにある
// ワードプレスのアドミン > ポスト >Add Media > Media Library > Upload
// サイトへ移行 ⇒画像のアドレスをコピー
// このアドレスが、ターミナルで見たところの、upload ディレクトリ

これから自動化すること
// EC2 インスタンスに画像をアップロード
// 上記メディアファイルを S3 にプッシュ
// 上記 S3 が CloudFront CDN の Origin となるようにする
// ユーザーはその URL から画像を見るようにする

EC2 インスタンスのメディアファイルを S3 にコピーするコマンド
// aws s3 cp --recursive wp-content/uploads s3://qqqqq-wordpress-media

変更分だけコピー/削除 つまりは 同期するコマンド
// sync を使う + dryrun
// aws s3 sync --delete /var/www/html/wp-content/uploads s3://qqqqq-wordpress-media --dryrun
 // ⇒ 実際に同期してみる

準備
CloudFront の DomainName のランダム部分を取得
// CloudFront の、 wordpress-media なんちゃらを Origin とする Distribution の、
// DomainName の、 .cloudfront.net の前の部分をメモっておく
// d3dj0uq1234567.cloudfront.net


wordpress にアップロードされた画像の URL を クラウドフロントの URL に書き換えるよう設定
// /var/www/html/.htaccess に下記内容を記載

 // Options +FollowSymlinks
 // RewriteEngine on
 // rewriterule ^wp-content/uploads/(.*)$ http://d3dj0uqwb2t107.cloudfront.net/$1 [r=301,nc]
 //
 // # BEGIN WordPress
 //
 // # END WordPress

rewriterule の部分の解説
この文字で始まる URL を、 CloudFront の URL に書き換える

httpd を再起動
 // service httpd restart

WordPress の画像の URL を確認
// // WordPress のページの、画像を右クリック ⇒画像のアドレスをコピー
// アドレスが CloudFront のそれに変わっているのを確認

EC2 と S3 との同期を自動化

クーロンを編集
// cd /etc
// vi crontab

// # Example of job definition:
// # .---------------- minute (0 - 59)
// # |  .------------- hour (0 - 23)
// # |  |  .---------- day of month (1 - 31)
// # |  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
// # |  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
// # |  |  |  |  |
// # *  *  *  *  * user-name command to be executed

// # 5 分に一回、root ユーザーで、さっきの EC2 と S3 を同期するコマンドを叩く
// */5 * * * * root aws s3 sync --delete /var/www/html/wp-content/uploads s3://qqqqq-wordpress-media
// # コマンドファイルを同期
// */5 * * * * root aws s3 sync --delete /var/www/html s3://qqqqq-wordpress-code

クーロン再起動
// service crond restart

画像をアップロードした時、アップロード →画像反映までに時間が掛かるっぽい
-------------------------------------------------




▼ AMI セットアップ
-------------------------------------------------
EC2 > ターゲットグループ
My-ALB のターゲットから MyWebServers を削除

クラシックロードバランサを作成

ブログを書くチームが入る EC2 という想定
// MywriteELB
// セキュリティグループ →WebDMZ
// 間隔: 6  正常のしきい値:3
// EC2 インスタンスの追加 → MyEC2WebServer
// Name: mywriteELB

EC2 インスタンス MyEC2WebServer を停止
 →パブリック IPv4 が消える

EC2 インスタンスが止まった時、IPv4 アドレスが消える事を防ぐ方法
// 1.Elastic IP を設定
// 1.EC2 を ロードバランサの後ろに配置して、DNS 名を使う

A レコードとして、書き込み用 ELB をターゲットとしたものを作成
// Route53 に移動
// DomainName を選択 →CreateRecordSet
// Name: write
// Alias: Yes
// Target: mywriteELB

停止中の MyEC2WebServer からイメージを作成
// EC2 > イメージ > イメージの作成
// イメージ名: MyWP-Write-Image
// 説明: MyWP-Write-Image
//  →作成

 →AMI 作成したイメージが available になるのを確認
  →インスタンス MyEC2WebServer > アクション →開始


-------------------------------------------------
