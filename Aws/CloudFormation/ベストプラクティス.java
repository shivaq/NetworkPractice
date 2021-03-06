■■ ベストプラクティス ■■

▼ レイヤード vs サービス指向 アーキテクチャー の違い
組織の方針によって分かれる
-------------------------------------------------
Layered architecture// horizontal layers
ネットワークレイヤー
インスタンスレイヤー
アプリケーションレイヤー
と分けて構築している
-------------------------------------------------
service oriented architecture// vertical layer
すべての CloudFormation が、
1 つのサービスのネットワークやアプリケーションを含んでいる
-------------------------------------------------



▼ デバッグ
-------------------------------------------------
・まずは、小さなスタンドアローンな Stack を作って、ユニットテスト的なことをしましょう。
・リソースタイプによって、作成/削除 時間が異なる事を知りましょう。
// AWS::CloudFront::Distribution →30-60min
// AWS::EC2::SecurityGroup → 数秒
・一部のエラーが、全体のスタックやり直しにつながる
・インクリメンタルな更新という形でスタックを大きくしましょう
一度に 1 つか 2,3 のリソースを追加 →失敗しても、ロールバックされる
・イベント をみて、スタック作成/更新 の進捗をチェックしましょう

・EC2 にログインして、 /var/log/boot.log を tail しましょう

失敗した理由をデバッグしたい場合
テンプレートオプション で失敗時のロールバック を しないを選択
-------------------------------------------------
デザイナーで検証
Yaml のフォーマットチェック。
// aws cloudformation validate-template
// Key のタイポとかはチェックしてくれない
-------------------------------------------------



▼ troposphere
-------------------------------------------------
テンプレートを抽象化して作成。
YAML 化の段階で検証してくれる。
-------------------------------------------------






▼ クロススタックリファレンスを使いましょう
-------------------------------------------------
ネットワークスタックから、
VPC や サブネットを 参照する
再利用が容易になる
-------------------------------------------------


▼ 各環境と密に結合したテンプレートにならないようにしましょう
-------------------------------------------------
dev/test/prod
リージョン や アカウント が違っても利用できるように。
-------------------------------------------------




▼ CloudFormation に credential を組み込まないようにしましょう
-------------------------------------------------
NoEcho パラメータや KMSを を使いましょう
-------------------------------------------------


▼ パラメータには、常に type や constraints を使えないか試しましょう


▼ EC2 インスタンスを使う時は、必ず CFN-Init を使いましょう

▼ テンプレートを実行する前に、必ず validate しましょう

▼ スタックの要素に対して手動で何かをしたりしてはいけません
state が mismatch になりかねません。
必ず CloudFormation 経由で行いましょう


▼ 変更は changesets を使って verify しましょうそうすれば、災害可能性が低くなる
https://docs.aws.amazon.com/ja_jp/AWSCloudFormation/latest/UserGuide/using-cfn-updating-stacks-changesets.html


▼ Deletion ポリシーを必ず使いましょう
-------------------------------------------------
