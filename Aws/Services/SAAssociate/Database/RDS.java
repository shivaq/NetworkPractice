▼ RDS // Relational Database Service
-------------------------------------------------
・Primary RDS から Secondary RDS にレプリケートする際、
その際の送信に課金はされない

・デフォルトボリューム Max 6 TB// Oracle と MySQL の RDS
・デフォルト IOPS Max →30000 IOPS// RDS インスタンス

SQL Server, Oracle, MySQL Server, PostgreSQL , Aurola, MariaDB,
-------------------------------------------------







Encryption at rest // アクティブじゃないデータの暗号化
-------------------------------------------------
AWS KMS(Key Management System) を通じて暗号化される
RDS インスタンスが暗号化されると、
配下のデータ at rest も暗号化される// バックアップやスナップショットや読み込んだレプリケーションデータも

サポートしてない →既存の DB インスタンスの暗号化
それでも暗号化したい場合、
暗号化を有効にした新規 DB  インスタンス作成後、そこにデータをmigrateする方法をとる。

暗号化サポート対象
MySQL, Oracle, SQL Server, PostgreSQL, MariaDB
-------------------------------------------------







▼ Multi AZ
-------------------------------------------------
パフォーマンス改善には使えない
// セカンダリ の DB を読み込み対象ノードに使うことはできない
// DR (Disaster Recovery) の用途には使える。

パフォーマンス改善をしたい場合
// Read Replica を使いましょう

ELB --> 3 EC2 --> US-EAST-1A(primary) <--> US-EAST-1B(secondary)
// ロードバランシングしてるEC2 と紐付いた RDS が、他の AZ の RDS と レプリケートできる
// 同期は AWS がハンドルしてくれる
// primary がダウンしても、DB のメンテナンスでダウンさせても、AZ が フェイルしても
// フェイルオーバーできる

・MySQL Server, Oracle, SQL Server, PostgreSQL, MariaDB
-------------------------------------------------







▼ Read Replica
-------------------------------------------------
・スケーリングに最適。
・DB の ReadOnly コピーが作れる
・AutomaticBackup が有効化されている必要あり
・メイン DB の Read Replica は 5 つまで作成可能
・Read Replica の Read Replica も作成可能 // レイテンシーは落ちる
・各 ReadReplica は、自身の DNS エンドポイントがある
・Multi AZ の ソース DB の ReadReplica は作成できる

// ELB --> 3 EC2 --> RDS RDS RDS 非同期レプリケーションを行う
// 加えて、
// レプリカ DB を使って 読み込みを ロードバランシング できる →パフォーマンス向上も可能

テストにも使える。
メインの production DB のリードレプリカを作成
 →メインとのレプリケーションを切って、独自に書き込みを行い、テストに使える

・MySQL Server, MariaDB, PostgreSQL // PostgreSQL は セカンドリージョンでは使えない

・インスタンスタイプを変更可能
-------------------------------------------------






ラボの流れ
-------------------------------------------------
無料枠でインスタンスを作成

MySQL のポート に対し、作成済みの DMZ セキュリティグループを紐付け
// デフォルトとして新規に作成されたセキュリティグループを見る
// 編集
// MySQL のポート 3306 を編集し、DB を利用する webServer の SG とだけ紐付ける
// タイプ →MySQL/Aurora ソース →カスタム →sg を入力 →既存のセキュリティグループからの接続にする

EC2 インスタンス作成
下記ブートストラップ をセット
// #!/bin/bash
// yum install httpd php php-mysql -y
// yum update -y
// chkconfig httpd on
// service httpd start
// echo "<?php phpinfo();?>" > /var/www/html/index.php
// cd /var/www/html
下記は、connect.php を取得しているが、どうせ後で修正するので不要かも
// wget https://s3.eu-west-2.amazonaws.com/acloudguru-example/connect.php

EC2 のアドレスが MySQL に接続できないことを確認
// ブラウザで EC2 の IP アドレスを叩いてみる
// 下記も叩いてMySQL に接続できないことを確認
// http://54.248.70.232/connect.php

EC2 に入って connect.php を作成
// sudo su
// vi /var/www/html/connect.php

下記を修正。hostname は
RDS インスタンスからコピーした エンドポイント:3306
// <?php
$username = "name";
$password = "passw0rd";
$hostname = "name.cpqixs7ouyxf.ap-northeast-1.rds.amazonaws.com:3306";
$dbname = "name";
//
// //connection to the database
// $dbhandle = mysql_connect($hostname, $username, $password) or die("Unable to connect to MySQL");
// echo "Connected to MySQL using username - $username, password - $password, host - $hostname<br>";
// $selected = mysql_select_db("$dbname",$dbhandle)   or die("Unable to connect to MySQL DB - check the database name and try again.");
// ?>

RDS サービスに接続できない際の原因の一つを確認
// rds 用に作成した セキュリティグループから、
// MySQL 用ポートと、いつも使ってる DMZ セキュリティグループのグループID
// とを紐付けた インバウンドルールを削除すると、接続不可能になる。
// 紐付け直せば 即座に接続可能になる。
