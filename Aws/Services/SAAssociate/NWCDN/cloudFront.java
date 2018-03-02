▼ CloudFront
CDN を使ったサービスの総称。
Origin が AWS じゃなくても、キャッシュしてくれる。

▼ CDN
-------------------------------------------------
Web ページやそのコンテンツなんかを、
ユーザーのロケーションや、コンテンツの配信サーバが置かれている場所に基いて
ユーザーに届けるネットワークシステム。
-------------------------------------------------

▼ Edge Location
コンテンツがキャッシュされる場所。 リージョンや AZ とは別の場所
TTL の間キャッシュされている// Time to live。24日とか
・ReadOnly ではない。
書き込むこともできる// オブジェクトをプットしたり
・キャッシュをクリアできるが、課金される。
新しい動画に更新したい場合とかに、クリアする

▼ Origin
元のファイルのいる場所。バケツやEC2インスタンスや、ELバランサーやRoute53

▼ Distribution
-------------------------------------------------
Edge Locations 達によって構成される CDNetwork のことを、
Distribution とも呼ぶ

▼ Web Distribution
Web サイトが使う Distribution

▼ RTMP // Distribution のうちのひとつ
// Real-Time Messaging Protocol
Media ストリーミングが使う
Flash が多い。
-------------------------------------------------






▼ Create Distribution
-------------------------------------------------
・Restrict Bucket Access
S3 の 直URLアクセス を禁止し、CloudFront 経由でのアクセスを強いる

・Grant Read Permissions on Bucket
この Origin の Distribution 作成時に、
CloudFront に S3 の Origin へのread permission を
自動的に与えるか、手動で与えるか。




・Default TTL 86400 →24H

・Restrict Viewer Access
(Use Signed URLs or Signed Cookies)
購読者だけ動画を見ることができるとか、そういった場合に使う

・Alteranate Domain Names(CNAMEs)
ドメインネームを割り当てる
-------------------------------------------------
CloudFront を適用した結果、s3-eu-west-1 から、アドレスが変わる
https://s3-eu-west-1.amazonaws.com/shivaqireland/blond_woman_bob_jersey1.png
https://d3bdx9c5575ij9.cloudfront.net/blond_woman_bob_jersey1.png
-------------------------------------------------
Edit
-------------------------------------------------
Invalidations
キャッシュされてる OBJ を、TTL を待たずに invalidate にできる。有料。
-------------------------------------------------
