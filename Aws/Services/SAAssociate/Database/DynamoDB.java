▼ DynamoDB とは
-------------------------------------------------
・SSD ストレージに格納されている
・３つの地理の離れた DC に格納される



NoSQL
NonRelationalDatabase
どんなスケールでも、数ミリセカンドのレイテンシーが実現可能。


DynamoDB →Document Oriented Database

Database
 ・Collection // Table みたいなもの。{"_id":"1234"} といった {} の塊
 ・Document // Row
 ・Key/Value // Fields

柔軟かつパフォーマンスも良いので、
モバイル、Web,ゲーム、IOT まで色んなアプリに向いている。
-------------------------------------------------






▼ データの一貫性オプション
-------------------------------------------------
書き込み後、１秒以内に結果を読み込みたいかどうかで使い分ける

・Eventual Consistent Reads(Default)
// 全データコピー間での一貫性は、１秒以内に保たれる。

・Strongly Consistent Reads
// 全書き込みを反映させた上で、結果を読み込む
-------------------------------------------------









▼ 課金ルール
-------------------------------------------------
どんな場合に DynamoDB が向いているか

// 書き込みは高くなる可能性があるが、読み込みは安い。
// 書き込みの数が少なく、読み込みが多く、スケーラブルでパフォーマンスの良いものを求めており、
// JOIN したクエリが不要な場合、
// RDS より DynamoDB がよいでしょう。
-------------------------------------------------
スループットによって料金が違う
週一とかで、Provision したユニットと、Consumed なユニットとのグラフを確認
// テーブル > メトリクス
キャパシティは手動で変更可能
// テーブル > キャパシティ

書き込みスループット
// 10 ユニットごとに、1時間あたり 0.0065 ドル

読み込みするプット
// 50 ユニットごとに、1時間あたり 0.0065 ドル


例
3GB のデータを保持しており、
1日あたり 100万 の読み込み 100万 の書き込みが行われているとする。

・1 秒あたり必要な 読み書き/sec を計算する
// 100万/24/60(分)/60(秒) = 11.6 書き込み/秒

// 1秒あたり 1 ユニットの書き込みが可能
//  →12 書き込み キャパシティUnit が必要
//
// 1秒あたり 1 ユニットの読み込みが可能
//  →12 読み込み キャパシティUnit が必要
//
// 書き込みキャパシティUnit = (0.0065/10) * 12 * 24 = 0.1872 ドル/日
// 読み込みキャパシティUnit = (0.0065/50) * 12 * 24 = 0.0374 ドル/日
-------------------------------------------------

Reserved Capacity
1-3 年契約をすると、その分値引きされる
-------------------------------------------------