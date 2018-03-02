そもそも話
-------------------------------------------------
Ansible は何のため？
Infrastructure as code
// configuration management tool
// 手動でやる処理を自動化する
// 結果、エラーが無くなる。素早く展開できる。再利用できる。
// コードだから、レビューもできる。
// 誰がどこをどう変えたか、バージョンコントロールが簡単

// bash,perl, Python スクリプトなどと比べ、
// ansible, puppet, chef は抽象化がなされている。
// クラスプラットフォームの実装も、細かいことは隠蔽してくれてる

an idempotent operation とは
 // →同じパラメータで複数回呼ばれても、追加の変更をしない
 // abs(abs(x)) = abs(x) 何度やろうが、答えは x
 // ある操作に対するネットワークリクエストを一度だけ行いたい。
 // しかし、複数回リクエストがなされる可能性がある。

Agentless
// Chef や Puppet は エージェントを対象のサーバにインストールしておく必要がある
// Ansible は不要
-------------------------------------------------





▼ python 2 入っていない問題//UBUNTU とか
-------------------------------------------------
下記確認。
http://docs.ansible.com/ansible/latest/python_3_support.html
https://gist.github.com/gwillem/4ba393dceb55e5ae276a87300f6b8e6f
-------------------------------------------------








▼ ansible のコマンド達
-------------------------------------------------
簡単な操作 ping や シャットダウンなど であれば、
Playbook ではなく アドホックなコマンドラインで実行すればいい
-------------------------------------------------
基本フォーマット
// ansible <target> -m <module name> -a arguments
// module  ⇒リモートホストで実行されるコマンドのこと
-------------------------------------------------

ansible の モジュールリストを見る
// ansible-doc -l

ansible のモジュールドキュメントを見る
// ansible-doc <module name>
// = がついてるオプションは mandatory(必須)

ファイルをコピーする
// ansible demo_hosts -m copy -a "src=/tmp/test1 dest=/tmp/test1"
-------------------------------------------------






▼ ベストプラクティス
-------------------------------------------------
ansible コマンドを打つ前に、コネクトできるかどうかを確認
// ansible [somenodes,all] -m ping
// inventory の デフォルト確認対象 ⇒/etc/ansible/hosts
-------------------------------------------------





同様の設定をしたサーバの用意
-------------------------------------------------
// Ansible Controller
// Target Server1
// Target Server2
Ansible のインストール
-------------------------------------------------
// 下記参照
http://docs.ansible.com/ansible/latest/intro_installation.html#latest-releases-via-apt-debian

Amazon Linux AMI にインストールする場合のコマンド
// sudo pip install ansible

インストール成功を確認
// ansible →ヘルプが表示されたらOK

PlayBook 適用前の状態にリセットできるよう、スナップショットをとる
// VirtualBox マネージャー＞ マシンツール →スナップショット
クローンを作る
-------------------------------------------------



▼pem ファイルを登録して、引数に pem ファイル指定不要にする
-------------------------------------------------
・ssh-agent が、公開鍵認証時の 秘密鍵を管理してくれる
ssh-agent プロセスを起動させる
// eval `ssh-agent -s`
秘密鍵を ssh-agent に渡す
// ssh-add ansibleWS.pem
-------------------------------------------------

永続的に Pem ファイルを参照させるヒントがここにある。しかし、ansible 知識が今は足りない
2018/02/21
https://stackoverflow.com/questions/33795607/how-to-define-ssh-private-key-for-servers-fetched-by-dynamic-inventory-in-files/34332794
Related Discussions がよさげ
http://grokbase.com/t/gg/ansible-project/155y8wzgt8/any-way-to-specify-ssh-key-file-for-ec2-host-using-ec2-inventory
-------------------------------------------------










▼ ansible.cfg の中身
-------------------------------------------------
インベントリファイルのある場所
// inventory      = /etc/ansible/hosts
モジュールを探す場所 ; を使って、複数の場所を探させることも可能
// library        = /usr/share/my_modules/
// module_utils   = /usr/share/my_module_utils/

プレイブックでルートを指定してない時のデフォルト
// sudo_user      = root

SSH のポート 22 が使えない場合、ここを修正
// remote_port    = 22

新しいマシンにSSH 接続した時、信頼するかどうか聞かれないようにする場合
// host_key_checking = False
// 有効なままだと、下記エラーが出る
// "Using a SSH password instead of a key is not possible" の類のエラー

はいレイテンシーの場合、タイムアウト時間を変えたり
// # SSH timeout
// #timeout = 10

ログはデフォルトで無効になっている
// #log_path = /var/log/ansible.log

コンフィデンシャルな情報を格納する場所。パスワードとか
// #vault_password_file = /path/to/vault_password_file

ブラックリストで収集 fact 指定
// gather_subset = !hardware


-------------------------------------------------



▼ fact
// ターゲットホストのシステム環境の情報を取得する機能。
-------------------------------------------------
・ホストの状況をチェック ⇒それに応じて処理を行う
・状況 ⇒OS、RAM の容量、IPアド、ネットワークインターフェイス、HW,カーネル
・処理 ⇒グループ生成
-------------------------------------------------
・setup という モジュールを使って収集されてる//明示的にではなく暗示的に
・task を走らせる前に収集
・全体を収集してる

・task を走らせる前に、ansible は facts を収集する
・factの実行には時間がかかる
fact の収集不要の場合
// - hosts: localhost
//   gather_facts: no
-------------------------------------------------










▼ 変数
-------------------------------------------------
// 他のホストの IP アドレスを、更に別の ホストの設定に使ったり
// ハイフンは変数メインに使えない
-------------------------------------------------




▼ vault
-------------------------------------------------
vault に key/value ペアのテキストを保存
// ansible-vault create test.yml

vault の中身を復号して確認
// ansible-vault view test.yml

vault 内の値を参照している playbook を走らせる
// ansible-playbook playbook.yml -i hosts --ask-vault-password


-------------------------------------------------












YAML// yet another markup language
-------------------------------------------------
Dictionary と リスト。Orderd か UnOrderd か
// Dictionary には key/value がある。
// Dictionary はプロパティの順序が違っても、key/value が一緒なら同じ Dictionary
-
  name: unko
  hosts: localhost
  tasks: someTasks// - がリストのアイテムの１単位となる
// List には アイテムが有るだけ
// List は、順序が違ったら、違うリストとして扱われる
tasks:
 - name: name1 is the first
 - name: name2 is the second
-------------------------------------------------

▼ YAML と Python の関係性
-------------------------------------------------
▼ Yaml 形式
>>yaml.load(""
Foods:
 - eggs
 - pizza
Drinks:
 - water
 - vodka
"")

▼ 上記を Python 形式にして出力すると、、、
{'foods':['eggs','pizza'],
'drinks':['water','vodka']}
-------------------------------------------------
