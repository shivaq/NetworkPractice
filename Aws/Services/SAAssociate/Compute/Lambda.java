▼ Lambda とは
-------------------------------------------------
イベント・ドリブン コンピュート
// イベントに対応して、Lambda がコードを走らせる。
・イベントは、データに変換されて、S3 バケツや ダイナモDBにて保持することも可能
・HTTP リクエストをトリガーに、アマゾン API ゲートウェイや、AWS SDK を使って作られた API コールがコードを走らせる。

・1 イベント = 1 function

・Lambda は別の Lambda のトリガーになることも可能
 ⇒その場合、1 イベントが複数の function となる

・アーキテクチャが 複雑化 しがち。AWS X-Ray を使ってデバッグできる

・Lambda はグローバルに動ける。S3 バケツを他の S3 バケツにバックアップしたり。

Node.js, java, Python, C# を使える
// 例）
// トリガー:webServer にユーザーが画像をアップロード
// 実行：テキストの上の部分に画像を配置

アレクサは Lambda で運用されている
-------------------------------------------------



▼ 課金ルール
-------------------------------------------------
 - リクエスト数ごと
// ・100万リクエストまで無料
// ・100万リクエストあたり20セント
// クラウドGuru が課金され始めたのは6ヶ月前から。現在生徒数 25万人。

 - Duration 単位
・コードが実行され始めてから、返すまたは終端するまで。100ms程度。
・関数に割り当てたメモリによって価格は変わる。
・0.00001667ドル GB/sec
・Duration は最大 5分まで。それ以上かかるものは実行できないため、分解必要。
-------------------------------------------------




▼ Cool な部分
-------------------------------------------------
・サーバーいらずで管理不要
 // ⇒DB アドミン、NW アドミン、システムアドミン 不要
 // EC2 やその環境の管理とか気にしなくていい
 // ユーザーがサイトを訪れたら、Lambda に従って対応してくれる

・持続的スケーリング
 // 100万ユーザーがリクエストしても、API ゲートウェイが自動スケール
 // AutoScaling との違い ⇒あっちは数分かかる。Lambda は即座。

・超安い
 // Cloud GURU の経験談
 //  競合は自前の DC を持ち、自前のシステムアドミン、NWアドミン を持ち、それにコストがかかっている。
 //  GURU はコードのディベロッパがいるだけで、全コードを Lambda にアップロードしてあとはおまかせ。
 // サーバの管理とか全くしていない
-------------------------------------------------




クラウド GURU の事例
-------------------------------------------------
// Angular.js を使って 各クライアントデバイスに対応
// API ゲートウェイを仕様
// Lambdaがトリガー
// 動画は S3 に保存してる

ユーザーがサイトにアクセス
 Route53 に登録された ドメインネームなので、そいつが該当する S3 に名前解決
 接続され web ページが表示される。

ユーザーがボタンをクリック GET リクエスト⇒Lambda が走って API ゲートウェイへ
API ゲートウェイが トリガーとなり、Lambda を走らせる。
Lambda がユーザー名を出力
 ⇒API ゲートウェイ ⇒S3 ⇒ユーザー Hello

-------------------------------------------------















Lambda テスト 一連の流れ
-------------------------------------------------
関数作成
// 名前、ロール名をつける
// ランタイムは好きな言語を
// ポリシーテンプレートが、新規ロールに追加される


コードの保存
-------------------------------------------------
関数コード
// 走らせるコードを記載

基本設定
// 説明とかを記載
-------------------------------------------------

トリガーの追加
-------------------------------------------------
API ゲートウェイ

API を作成 //既存のAPI を選択できる
デプロイされるステージ
prod //任意の名前

セキュリティ
AWS IAM
 ⇒追加 ⇒保存
 -------------------------------------------------


API ゲートウェイの設定
-------------------------------------------------
API ゲートウェイ名を選択

ANY は危険なので、アクション > メソッドの削除
 ⇒アクション> メソッドの作成 ⇒GET ✔
  ⇒セットアップ Lambda プロキシ統合の使用 ⇒Lambda リージョン（作成したリージョンを選択） Lambda関数（作成したやつを選択）⇒保存

アクション > API のデプロイ

ステージ > URL の呼び出しについて
ステージ名を選択した時のリンク ⇒{"message":"Missing Authentication Token"}
メソッド名選択時のリンク ⇒GET の場合、HTTP リクエストのコードが 200 の場合、Lambdaが走ってレスポンスとして返ってくる
-------------------------------------------------


設定したトリガーの使い方
-------------------------------------------------
上記例で言えば、GET の時のリンクをコピーしておく。
で、
S3 に index.html をセットしたとして、
そのファイルを下記のようにする

下記の第二引数に、API ゲートウェイの、GET のリンクを貼る
void open(DOMString method, DOMString url, optional boolean async,
// optional DOMString? user, optional DOMString? password
);

結果どうなるか
ボタンをクリックして 200 が返ってくると、Lambda にセットした画像や文字列やらが返ってくる //今回は文字列
その返り値が、対象の id の部分に挿入される
-------------------------------------------------
// <html>
// <script>
//
// function myFunction() {
//     var xhttp = new XMLHttpRequest();
//     xhttp.onreadystatechange = function() {
//         if (this.readyState == 4 && this.status == 200) {
//         document.getElementById("my-demo").innerHTML = this.responseText;
//         }
//     };
    xhttp.open("GET", "https://22nx7t6vq9.execute-api.ap-northeast-1.amazonaws.com/propro/myLambdaTest2", true);
//     xhttp.send();
//
// }
//
// </script>
// <body><div align="center"><br><br><br><br>
// <h1>Hello <span id="my-demo">Cloud Gurus!</span></h1>
// <button onclick="myFunction()">Click me</button><br>
// <img src="https://s3.amazonaws.com/acloudguru-opsworkslab-donotdelete/ACG_Austin.JPG"></div>
// </body>
// </html>
-------------------------------------------------




















▼ スケールアップ VS スケールアウト？
-------------------------------------------------
スケールアップ
// リソースの量を増やす// RAMは の容量とか

スケールアウト
// インスタンスの数を増やす。
// ロードバランシングみたいに。
-------------------------------------------------





▼ Lambda がオートスケールアウトするとはどういうことだ？
-------------------------------------------------
Lambda はオートスケールアウトするので、ロードバランシングも気にしなくていい。
複数のユーザーが、 Hello World するコードのトリガーを走らせても、
スケールアウトを自動的に行なってくれる

・ユーザーが HTTP リクエストを、 API ゲートウェイにするたびに、
新しい Lambda 関数が起動する

 = 二人のユーザーがHTTPリクエストをすると、二つのLambdaが立ち上がり、
合わせて二つのレスポンスが返っていく。
コードは一つ。立ち上がるLambdaはユーザーのリクエスト数に応じて増減
-------------------------------------------------






















▼ データセンター時代
-------------------------------------------------
とりあえず、ハードウェアをデータセンターに集めて、その管理を任せられるようにした。
// スイッチやルータやらの障害対応などを気にする必要があった
// で、Webサーバの構築やDBサーバの構築やらにも時間がかかった
-------------------------------------------------

▼  IAAS 時代
-------------------------------------------------
EC2 // Elastic compute cloud 2006 登場
ブラウザのコマンドラインを使って、
 VM のプロビジョニングができるようになった
 // AWS は インフラストラクチャ as code な存在となった
 //
 // それでもまだ、それらは物理サーバ上の VM です。
 // ディスクが壊れたら、データは失われてしまう。
 // サーバが壊れたら、OSを再インストールしなければならない
-------------------------------------------------

▼ PAAS 時代
-------------------------------------------------
// Elastic_Beanstalk
// これの誕生が、IAAS から PAAS の時代への変革の象徴
開発者はコードをアップロードするだけ。
それを動かすインフラ(Webサーバがどうのこうのとか)は気にしなくていい
//
// それでも、Windows や Linux といった OS の管理を気にする必要があった
-------------------------------------------------

▼ コンテナ流行時代
-------------------------------------------------
EC2_Container_Service
Docker とかのコンテナサービス
軽くて隔離されてる。

// それでも、サーバのデプロイは必要。
// コンテナが走っているかどうかを気にしなければいけないし、
// 負荷にも気を使う必要がある
-------------------------------------------------


▼ Lambda 時代
-------------------------------------------------
// 2015 登場
// データセンター、HW、インフラ、プロトコル、OS、コンテナ、
// アプリケーションレイヤー、AWS API。
上記が全部カプセル化されている。
// パッチもスケーリングも気にしなくていい。
コードのことだけ、気にしていればいい。
// それを走らせるトリガーだけ気にしていればいい。
-------------------------------------------------
