AWS コンポーネント

フォーマット
// AWS::サービス名:: データタイプ名

DependsOn:
// 二つのリソース間の依存関係を表現
// 例
// ASG を作成した後に、ECS クラスタを作成する



DeletionPolicy:
// CloudFormation が削除されても、RDS は削除させないとか
・Delete
// リソースとコンテンツを全部削除
// S3 には適用されない

・Retain
// スタックが削除されても、リソースもコンテンツも削除されない。

・Snapshot
// snapshot をサポートするリソースにのみ対応
// EC2 ボリューム、ElasticCache RDS Redshift など
// 削除前にスナップショットを作成する
