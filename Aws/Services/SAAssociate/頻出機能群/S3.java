

▼ S3
-------------------------------------------------
Object based storage
// 格納するもの
// PDF や Movie などの Flat Files
// 格納しないもの
// OS。そこから DB にクエリしたりもしない。

// ミニマムサイズは 1Byte
// ・1 ファイルあたり 0 ～ 5TB
// ・バケツと言ってるのは、フォルダと考えていい
// ・ユニバーサルな名前空間。グローバリーにユニークにする。
// ・バケツを作ればDNSファイル アドレスが生成される。
// https://s-3-eu-west-1.amazonaws.com/mybucket
// ・アップロード成功 → HTTP 200 コードを受信
-------------------------------------------------
S3 の OBJ を構成するコンポーネント
-------------------------------------------------
// Key 名前
// Value データそのもの,
// バージョンID
// メタデータ
// サブリソース（AccessControlLists, Torrent）
-------------------------------------------------


▼ S３ の課金対象
-------------------------------------------------
・ストレージ容量
・データリクエスト
・データへのタグ付け
// このデータは HR 関連 とか。フォルダ管理とかじゃ駄目なのか？
・データ転送
// レプリケートするときとか
S3 Transfer Acceleration
・長距離でも早くてセキュアなデータ送信
Edge Location を利用して、遠くに格納されたS3 に簡単アクセスできるよう調整してくれる
-------------------------------------------------
















▼ S3 Tiers/クラス
-------------------------------------------------
availability は 3つとも 9999
・標準
// 99.99% availability, 9 * 11 durability。2施設同時消失でも大丈夫
・標準 in IA（Infrequently Accessed)
// 年に一度見るくらいの給料明細とかに。
//  →S3 より安いけど、受信に課金される。
・ReducedRedundancyStorage
durability も 9999
// durability が 99.99%。1施設消失まで大丈夫
// 安い。再度生成できるデータとかに向いてる。サムネイルとか。
・Glacier
// すごく安い。アーカイブ化されている。取得に３－５時間かかる。
// 0.01$/GB
-------------------------------------------------












▼ Data Consistency Model For S3
-------------------------------------------------
・Read after Write consistency for PUTS of new Object
//  →新規 OBJ をS3 にプットすると同時に、consistency を取得。
// で、そのままそれを読むことができる

・Eventual Consistency for overwrite PUTS and DELETES
 // →既存の OBJ を更新したり削除したりした時は、タイムラグあり
・S3 へのアップデートは アトミック。
// 新しいデータか古いデータを取得することはあっても、壊れたデータは取得されない
-------------------------------------------------






▼ クロスリージョンレプリケート
-------------------------------------------------
東京とシドニーとかで、それぞれ バケツを作る

下記のように、この s3: から この s3: へコピーって感じでやる
// aws s3 cp --recursive s3://qqqqqbucket s3://qqqqqsydney

・しかし、アクセス権はコピーされない。公開ファイルでさえも。

・ソース側のファイルを削除すると、コピー先のそれも削除される。
削除マークもコピーされる。

・ソース側の、バージョニングの削除マークを削除しても、コピー先のそれは削除されない
-------------------------------------------------







▼ LifeCycle// バケツ > 管理 > ライフサイクル
-------------------------------------------------
保存期間等を条件に、
スタンダード →IA →アーカイブ →Gracier へと保存タイプが遷移し、やがて削除される。
そんなルール

▼ 名前とスコープ
プレフィックスとは →フォルダと考えていい
未指定 →全体にルール適用

▼ 移行
・バージョニングが有効な場合、以前のバージョンも適用対象
・移行を追加すると、IA、Gracier への移行期限をセットできる

▼ 有効期限
設定の失効を選択すると、削除期限を設定できる

▼ マルチパートアップロードを。。。
指定期間内に完了してないマルチパートアップロードがある場合、削除する
-------------------------------------------------









▼ S3 Transfer Acceleration
-------------------------------------------------
CloudFront の Edge Network を使って、
S3 へのアップロードを加速させる。

S3 とは別の URL を使うことで、Edge Location に直接アップロードし、
その後 S3 の Bucket へ転送される。
-------------------------------------------------
S3 > プロパティ > Transfer Acceleration
下記エンドポイントが表示される
エンドポイント: s3transferqqqqq.s3-accelerate.amazonaws.com
-------------------------------------------------




▼ Static website hosting
-------------------------------------------------
Route53 を使う場合は、Bucket Name をドメインネームに合わせると、
連携して使える。

・アクセス数が伸びても、自動スケールしてくれる。
-------------------------------------------------
S3 > プロパティ > Static website hosting
エンドポイント : http://qqqqqwebsite.s3-website-us-west-1.amazonaws.com
バケツ名.s3-website-region...

インデックスドキュメント、エラードキュメントを指定して保存。
 →同じ名前のファイルをアップ// パブリックに公開で。
  →エンドポイントのURL へ行けば、表示されるはず。
index.html を非公開にしてエンドポイントに行くと、
エラー表示されるはず// error.html は公開設定

-------------------------------------------------

















▼ 試験前にやること
S3 FAQ を読む。
