▼ 種類
-------------------------------------------------
S3// Simple Storage Service
// Buckets と呼ばれるところに、ファイルをUL
// flat files 用

Gracier
// 安い。
// データをアーカイブするのに向いてる
// 年に一度しかみないやつとか。

StorageGateway
// virtual appliances
// データをS3 に戻すのに使ったり

EFS
// Elastic File system
// Network にアタッチできる FileSystem
//
// NTFS ボリュームにファイルを格納して、
// 複数の VM にマウントできる

Snowball
// でかいデータを AWS に送信するのに使う
// テラバイトデータとか、ブロードバンドで送るより、
// データセンターにデータを持ってって手動で移したほうが速い
-------------------------------------------------
