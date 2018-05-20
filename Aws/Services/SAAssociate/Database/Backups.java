▼ AutomatedBackups
-------------------------------------------------
・バックアップ中は I/O は保留され、レイテンシーがぐっと上がる。
// 数秒
// 定義済み Window で行われる。

・オリジナルの RDS インスタンスを削除すれば AutomatedBackups も消える

DB を retention period 内で、秒単位で指定して復元できる。

retention period
// 1 ～ 35 日の間で設定可能。デフォルトは 7 日
// 一日のフルスナップショットを取得し、かつ
// 一日のトランザクションログを保持。

復元を実行すると
// 直近のデイリーバックアップを選択し、その日のトランザクションログを反映させる。

AutomatedBackups はデフォルトで有効化。
S3 に保存されており、S3 での保存分は無料。何GBだろうが。
-------------------------------------------------



DatabaseSnapshot
-------------------------------------------------
・DB スナップショットを取得する際、 I/O はサスペンドされる
// 数秒

・オリジナルの RDS インスタンスを削除しても保持されたまま。
手動で行われる。
-------------------------------------------------


復元
-------------------------------------------------
AutomatedBackups も スナップショットも、
復元されたDBは、新RDS インスタンスと 新 エンドポイントになる
// DB Instance Identifier を新しくセットする必要がある

スナップショットから 新 RDS を作成する時は、
リージョン変更、インスタンスタイプ変更などが可能
-------------------------------------------------


▼ リストア時に気をつけること
-------------------------------------------------
・リストア時に「DatabaseName、MasterUsername、MasterUserPassword」を指定すると、下記エラー
DatabaseName: !If [isRestore, !Ref "AWS::NoValue", !Ref DatabaseName]
MasterUsername: !If [isRestore, !Ref "AWS::NoValue", !Ref DatabaseUser]
MasterUserPassword: !If [isRestore, !Ref "AWS::NoValue", !Ref DatabasePassword]

・リストア時には 「SnapshotIdentifier」 を指定する
SnapshotIdentifier: !If [isRestore, !Ref SnapToRestore, !Ref "AWS::NoValue"]
-------------------------------------------------


  SnapshotIdentifier: !If [isRestore, !Ref SnapToRestore, !Ref "AWS::NoValue"]
