EFS// Elastic File system
-------------------------------------------------
・EC2 のための ストレージサービス
・ブロックベース。//S3 のような OBJベースではない
・S3 と同じく、 ReadAfterWrite コンシステンシー
・共有可能 NFSv4// Network File System ver4
・ArrayList のごとく、ファイルの追加/削除によって Elastic に伸び縮み
・pre-provisioning 不要 ⇒使った分だけ課金
・ペタバイトまで拡張可能
・数千同時接続
・リージョン内であれば、AZ をまたいで格納可能

ユースケースとして考えられること
ファイルサーバとして扱う。

-------------------------------------------------


ラボ
-------------------------------------------------
ポイント
EFS と EC2 を同じセキュリティグループにする
//EFS の Moutn targets 欄で Security groups を確認
// default だったりするから。

使用可能リージョンで、EFS を作成
3つくらいの AZ をマウントターゲットにする

EC2 を同一リージョンで、上記のそれぞれの AZ をサブネットとして作成
ELB を作成し、上記 EC2 をヘルスチェクさせる

セキュリティグループを合わせる

それぞれの EC2 にログイン ⇒ apache をインストール

EFS の FileSystems 画面で、
EC2 mount instructions を確認

EFS をマウントする。その際、マウント先を下記のように /var/www/html にすると。。。
// mount -t nfs4 -o nfsvers=4.1,rsize=1048576,wsize=1048576,hard,timeo=600,retrans=2 fs-d9847991.efs.us-east-1.amazonaws.com:/ /var/www/html

AZ 1 と AZ 2 とが同じ /var/www/html を共有できている状態になる。
同じNFS、同じファイルを共有しているため、それぞれで設定をいじったりしなくてもよくなる。
同じファイルをどこかからコピーしてくるようなコードを書く必要もない。





-------------------------------------------------
