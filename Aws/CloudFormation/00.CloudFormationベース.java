7:30 から

▼ 基本
-------------------------------------------------
テンプレートファイルの取り扱われ方
// テンプレートを選択すると、まずそれは自動生成された S3 バケツにアップロードされ、
// そのファイルが CF から参照される。

スタックの更新時に起きていること
// パラメータ等はそのままだが、元ファイル(リソース？)については、編集をするというより、
// アップロード前のテンプレートファイルを編集し、再アップロードして、
// AWS は前のバージョンとの変更点を検出し、それに応じてコンポートの削除や作成を行う

更新に伴う要件
更新プロパティによって異なる。アクセスの中断の有無 及び 置換

置換 →旧リソースは一旦削除される もしくは Deletion Policy によって削除できない
// バケツ名更新時、置換が行われるその際、古いバケツはそのまま削除 →新しい名前の空のバケツができる

スタック名
// スタックは名前で同定される。
// 名前はユニークでなければならない
// 更新時にスタック名を編集できない

テンプレートは 基本的に、静的に宣言されなければならない。
// 動的にリソースを生成させることはできない

一部サポートされない AWS サービスが有る。回避策もある。
// Lambda を使えばなんとかなる
-------------------------------------------------





▼ Amazon CLI を使って CloudFormation
-------------------------------------------------
名前をつけてプロファイル(リージョンやパスワードなど)を設定
// aws configure --profile my_defined_user
Amazon CLI を使って CloudFormation     各ファイルがあるディレクトリ上で実行
// aws cloudformation create-stack --stack-name cli-stack-sample-name --template-body file://templateFromCLI.yaml --parameters file://parameters.json --profile cf-course --region us-east-1
スタックを作成
// create-stack
スタックの名前を定義
// --stack-name cli-stack-sample-name
テンプレートファイルを参照
// --template-body file://templateFromCLI.yaml
パラメータファイルは json のみサポート
// --parameters file://parameters.json
名指しでプロファイルデータを選択
// --profile my_defined_user
リージョンを指定することもできる
// --region us-east-1

// # some options:
// # [--disable-rollback | --no-disable-rollback]
// # [--rollback-configuration <value>]
// # [--timeout-in-minutes <value>]
// # [--notification-arns <value>]
// # [--capabilities <value>]
// # [--resource-types <value>]
// # [--role-arn <value>]
// # [--on-failure <value>]
// # [--stack-policy-body <value>]
// # [--stack-policy-url <value>]
// # [--tags <value>]
// # [--client-request-token <value>]
// # [--enable-termination-protection | --no-enable-termination-protection]
// # [--cli-input-json <value>]
// # [--generate-cli-skeleton <value>]

-------------------------------------------------




▼ 書き方
-------------------------------------------------
YAML ファイルに記載した順番に関係なく、
CloudFormation は正しい順番で各コンポーネントを生成していく
// SecurityGroup -> EC2 instance -> EIP

key/value の value が空の場合は {}
-------------------------------------------------
