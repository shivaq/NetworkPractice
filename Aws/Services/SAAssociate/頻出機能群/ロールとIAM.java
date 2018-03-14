IAM ユーザーのサインインリンク:
https://qqqqq.signin.aws.amazon.com/console
上記からログイン


▼ ロール
-------------------------------------------------
ロールはグローバル。リージョン別とかにはならない。

アクセスキーや秘密アクセスキーを保存するよりセキュア

管理がかんたん

-------------------------------------------------



EC2 インスタンスにロールを与える方法
-------------------------------------------------
EC2 作成時、下記ステップで EC2 インスタンス自体に Role を与えられる
// ステップ 3: インスタンスの詳細の設定 ⇒IAM role

EC2 インスタンス＞説明＞IAM ロールから、Role 情報にアクセスできる

アクションから IAM ロールの追加、置換ができる
-------------------------------------------------







▼ロールタイプ
-------------------------------------------------
Role for cross-account access 系
 // 他の AWS アカウントのリソースにアクセスできる

Role for identity provider access 系
 // Amazon Cognito を使ったり、Facebook,Google アカウント認証を使える
 //  アプリで上記認証を使ったら、AWS への認証も与えるとかできる
-------------------------------------------------
