▼ SQS// Simple Queue Service
-------------------------------------------------
コンピューターがメッセージを処理するのを待っている間、
メッセージを格納して、そのメッセージキューにアクセスできる。

配信キューシステム。ウェブサービス・アプリがメッセージキュー


例
写真 をウェブサイトにアップロードする
 → で、サイトはその 写真を S3 に格納する
  →で、写真の S3 へのアップロードがLambdaのトリガーとなる
   →Lambda は写真に関するすべてのデータを SQS に送る


-------------------------------------------------
