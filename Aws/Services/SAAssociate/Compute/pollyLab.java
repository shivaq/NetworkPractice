リージョンはアメリカ東部



該当サービス用 ロールを作成
-------------------------------------------------
複数の機能を Lambda にさせるので、カスタムポリシーを作成したほうが便利

Json から作ったほうが早いので。。。とのことで、コピペ ⇒確認 //これで作成された
左ペインで ロールに戻り、改めてロールを作成 ⇒先程作ったカスタムポリシーを名前で検索
 ⇒適用
-------------------------------------------------


Lambda作成
-------------------------------------------------
PostReader_NewPosts

Python2.7
-------------------------------------------------



▼ API Gateway
-------------------------------------------------
各メソッド GET とか POST とか 別に、ゲートウェイアクションを設定

S3 と API Gateway とが交信できるようにする //双方ドメインネームが異なるので、この処理が必要


CORS の有効化 // CrossOriginResourceSharing


Get > メソッドリクエスト のリンクをクリック
 ⇒URL クエリ文字列パラメータ
  ⇒文字列パラメータを追加   postId

Get > 統合リクエストのリンクをクリック
 ⇒本文マッピングテンプレート
 テンプレートが定義されていない場合 を選択
  ⇒マッピングテンプレートの追加   application/json

配布資料にあるmapping.json の中身をコピー ⇒保存

リソース の / を選択 ⇒API のデプロイ

ステージは dev, dev, dev  ⇒デプロイでいい
 ⇒下記をコピー
https://35o8cfah58.execute-api.us-east-1.amazonaws.com/dev

website 用バケツを開く
 ⇒アクセス権限＞バケットポリシー
 配布資料にある 下記の内容をコピペ
bucketpolicypermissions.json
下記みたく上書き ⇒ 保存 パブリックになるが、webページなのでOK
"Resource": [
  "arn:aws:s3:::qqqqq-polly-website/*"
]

script
var API_ENDPOINT = "https://35o8cfah58.execute-api.us-east-1.amazonaws.com/dev"
-------------------------------------------------
