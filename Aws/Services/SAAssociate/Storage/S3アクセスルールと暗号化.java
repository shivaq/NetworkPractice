バケツへのセキュリティ設定方法

▼ 2つのレイヤーのアクセスルール
-------------------------------------------------
▼ Bucket Policies
バケツ全体に影響

▼ ACL
個々の OBJ に適用
-------------------------------------------------


▼ Encryption
-------------------------------------------------
▼ In Transit
バケツからバケツへ、PC からバケツへ送る時（トランジット。A から B へ送る）
HTTPS を使う// つまり、SSL/TLS を使う// SecureSocketLayer, TransportLayerSecurity

▼ At Rest
 →サーバーサイド Encryption
  ・S3 Managed Keys - SSE-S3
  各 OBJ を暗号化// ユニークなキーを使った MFE// マルチファクター暗号化
  // Amazon は そのキーをさらにマスターキーで暗号化。マスターキーは定期的に変えてる。
  // AESS256 を使用（Advanced Encryption Standard)
  ・AWS Key Management Service, Managed Key - SSE-KMS
  // SSE-S3 に、セキュア要素を更に追加
  //  - Envelope Key で、暗号キーをさらにエンベロープ。
  //  - Audit Trail で、誰がその OBJ を使ったかわかる
  ・Server Side Encryption With Customer Provided Keys - SSE-C
ユーザーのキーを使って SSE！
 →クライアントサイド Encryption
 クライアント側で暗号化した上で、S3 にアップロード
-------------------------------------------------
