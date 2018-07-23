■■ ベストプラクティス ■■
-------------------------------------------------
▼ ログ出力や、失敗時の処理を行う
関数が例外処理をするロジックを組んでおくこと。
トラブルシュートするためにも、失敗時のログが確認できるようにすること。
必要ならば、CFN に対して、処理が失敗したことを示す HTTPS を返すようにすること。

さもなければ、例外時はなんの応答も返ってこない。
CFN は 処理が成功したか失敗したかのHTTPS レスポンスを要求するだけで、
報告されない例外があっても、CFN はタイムアウトまで待ってロールバックするだけだから。


▼ 合理的なタイムアウトを設定し、超過する場合はレポートせよ
タイムアウト時間内に処理が終わらない場合、例外が走り、CFNには応答は送信されない。
それを回避するために、タイムアウトは処理時間及びネットワークの状況の変動に対応できる十分な長さに設定すること。

また、タイム・アウトしそうなときに、CFNに対しエラー応答するように設定すると便利。
こうすることで、関数のタイムアウトが、カスタムリソースのタイムアウトと遅延を引き起こすことが避けられる。




▼ CFN がどのように同定し、リソースを置換するかを理解せよ
更新によって物理リソースの置換が発生するとき、CFNは Lambda 関数から返される物理IDを、その前の物理リソースIDをと比較している。
ID が異なれば、CFN はリソースが新しい物理リソースと置換されたと推測する。

しかし、ロールバックの余地を残すために、古いリソースは暗黙的に削除されない。
スタックの更新が成功で完了したとき、削除イベントリクエストが、古いIDとともに送られる。
もしスタック更新が失敗してロールバックが起こった場合、新しい物理リソースIDが、削除イベントで送られる。
上記を考慮して、
新しい物理リソースIDを返すときは十分注意して、削除イベントは、置換を伴う更新を適切に処理するために入力となる物理IDが扱われることを確実にすること。



▼ 冪等性を考慮して関数をデザインせよ

冪等性を維持する関数は、同じインプットで何度でも繰り返しても、結果は一度だけ実行したときと同じ結果になる。
冪等性が担保することで、リトライ、更新、ロールバックが、重複したリソースやエラーの原因とならないようにすること。
例えば、
CFN が関数を起動してリソースを作成したにもかかわらず、作成成功のレスポンスを受信しなければ、その関数は再度起動されるかもしれない。
結果、２つ目のリソースが作成されてしまう

この問題の取扱は、関数が行おうとするアクションによって異なる。
しかし、共通のテクニックがある。
それは、CFNが既存のリソースをチェックするための一意のトークンを使うこと。
例えば、
スタックID及び論理リソースIDのハッシュは、リソースのメタデータまたは DynamoDB のテーブルに格納できる。


▼ ロールバックを考慮した関数にせよ
ロールバック時に置換を伴う場合、
置換が適切に処理され、古いリソースが、削除イベントを受信するまでは暗黙的に削除されないようにすること。
上記保証することで、ロールバックがスムーズに実行されるようになる。
カスタムリソースを使う場合、ベストプラクティスを適用するのに、下記の「カスタムリソースヘルパー」が例外とタイムアウトに陥ったときに、CFNに応答し、ログを吐く助けになる。
https://github.com/awslabs/aws-cloudformation-templates/tree/master/community/custom_resources/python_custom_resource_helper
-------------------------------------------------






■■ 用語説明 ■■
-------------------------------------------------
▼ template developer
カスタムリソースタイプを含むテンプレートを作成する。
サービストークンと
テンプレート内のすべての入力データを指定する。

▼ サービストークン
CFNがどこにリクエストを送るかを指定するもの。
SNSトピックのARN、Lambda関数のARN。

▼ custom resource provider
カスタムリソースを所有し、CFNからのリクエストへの応答を判断する。
サービストークンを返すのはこいつの役割。
サービストークンと、入力データの構造はこいつが定義する。

▼ CloudFormation
スタックが走っている間、サービストークンにリクエストを送る。
スタックの処理を進める前に、サービストークンからの応答を待つ。

▼ pre-signed URL(署名付きURL)
※pre-signed URL(署名付きURL)は、そのURLの作成者が、そのオブジェクトへのアクセス権を有している限り、
そのURLが指し示すオブジェクトへのアクセスを与えてくれる。
使いみちは、
ユーザーや顧客にバケットに特定のオブジェクトをアップロードを許可しつつ、
ユーザーや顧客にクレデンシャルや権限を要求しないような場合に有用。
あらゆるバケットやオブジェクトは、デフォルトでプライベート。
署名付きURLを作成するには、クレデンシャルが必要で、バケット名、オブジェクトキー、HTTPメソッド、
失効日時を指定する必要がある。

▼ CFN と VPC エンドポイント
VPC エンドポイントを使う場合、これを指定しないとスタックの処理が失敗する

CFN で VPCエンドポイントを使った、VPD内のリソースを生成する場合、
IAM エンドポイントポリシーを修正して、対象となる S3バケットへのアクセス権を付与する必要がある。
たとえば、
CFN がカスタムリソースや、wait 条件への応答を監視するためのS3バケットを使う場合、
VPCエンドポイントポリシーはユーザーに、下記Bucketへの応答を送る許可を与えなければならない。
カスタムリソース → cloudformation-custom-resource-response-リージョン名 バケット へのアクセス
wait condition →cloudformation-waitcondition-リージョン名 へのアクセス

例
// examplebucket に、ID vpce-1a2b3c4d の VPC エンドポイントからのみアクセスできるようにする S3 バケットポリシーの例
{
   "Version": "2012-10-17",
   "Id": "Policy1415115909152",
   "Statement": [
     {
       "Sid": "Access-to-specific-VPCE-only",
       "Action": "s3:*",
       "Effect": "Deny",
       "Resource": ["arn:aws:s3:::examplebucket",
                    "arn:aws:s3:::examplebucket/*"],
       "Condition": {
         "StringNotEquals": {
           "aws:sourceVpce": "vpce-1a2b3c4d"
         }
       },
       "Principal": "*"
     }
   ]
}
-------------------------------------------------




流れ
-------------------------------------------------
1.
template developer がテンプレート内でカスタムリソースを定義する。
その際に、サービストークンおよび入力データパラメータを定義しておく。

custom resource provider はサービストークンと、入力データの構造を定義する。
-------------------------------------------------
例）SNS
-------------------------------------------------
AWSTemplateFormatVersion: "2010-09-09"
Resources:


  MyFrontEndTest:
  // # 明示的にカスタムリソースタイプを定義したほうがわかりやすい
    Type: "Custom::PingTester"
    Version: "1.0"
    Properties:
      // # SNS または Lambda
      ServiceToken: "arn:aws:sns:us-east-1:84969EXAMPLE:CRTest"
      key1: string
      key2:
        - list
      key3:
        key4: map

Outputs:
  CustomResourceAttribute1:
    Value: !GetAtt MyFrontEndTest.responseKey1
  CustomResourceAttribute2:
    Value: !GetAtt MyFrontEndTest.responseKey2
-------------------------------------------------
例） Lambda
-------------------------------------------------
MyCustomResource:
  Type: "Custom::TestLambdaCrossStackRef"
  Properties:
    ServiceToken:
      !Sub |
        arn:aws:lambda:${AWS::Region}:${AWS::AccountId}:function:${LambdaFunctionName}
    // #下記のように、Lambda のインプットに使うプロパティを定義できる
    StackName:
      Ref: "NetworkStackName"
-------------------------------------------------


2.
スタック内でカスタムリソースが作成/更新/削除されるとき、
CFNがサービストークンにリクエストを送る。

リクエスト内で定義して置かなければいけないのは、
リクエストタイプ、カスタムリソースが応答を返す先となるS3のURL
例）リクエストは JSON で送られる
{
   "RequestType" : "Create",
   "ResponseURL" : "http://pre-signed-S3-url-for-response",
   "StackId" : "arn:aws:cloudformation:us-west-2:EXAMPLE/stack-name/guid",// カスタムリソースを含んだスタックのARN
   "RequestId" : "unique id for this create request",
   "ResourceType" : "Custom::TestResource", // template developer が定義したリソース名
   "LogicalResourceId" : "MyTestResource", // template developer が定義したカスタムリソースの論理ID
   "ResourceProperties" : {
      "Name" : "Value",
      "List" : [ "1", "2", "3" ]
   }
}


3.
custom resource provider はCFNからのリクエストを処理し、
pre-signed-S3-url へ SUCCESS、FAILED といった応答を返す。
レスポンスは、JSONフォーマットのファイルとしてS3にアップロードされる。

CFN は、URL にレスポンスが返ってくるのを待つ。

※ ここで、custom resource providerが Name/Value な情報を渡し、
template developer がそれにアクセスできる。
センシティブな情報が含まれる場合は、 NoEcho
さもないと、API 上でそのじょうほうが 露出する。

// カスタムリソースのレスポンス例
{
   "Status" : "SUCCESS",
   "PhysicalResourceId" : "TestResource1",
   "StackId" : "arn:aws:cloudformation:us-west-2:EXAMPLE:stack/stack-name/guid",
   "RequestId" : "unique id for this create request",
   "LogicalResourceId" : "MyTestResource",
   "Data" : {
      "OutputName1" : "Value1",
      "OutputName2" : "Value2",
   }
}

4.
SUCCESS レスポンスが確認できたら、CFN はスタックの処理を進める。

template developer 側は、レスポンスにある Output を、GetAtt を使って取得することができる。

-------------------------------------------------
