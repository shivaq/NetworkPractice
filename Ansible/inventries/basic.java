


▼inventory
-------------------------------------------------
デフォルトでチェックされるファイルパス
// /etc/ansible/hosts
INI フォーマットで書かれている

範囲指定でホストを指定できる
// www[001:006].example.com


/etc/ansible/hosts に ターゲットのアドレスを追加
// [demo_hosts]
// node01ForAnsible ansible_user=ec2-user
// node02ForAnsible ansible_user=ec2-user

/etc/hosts に、対象ノードの"プライベート"アドレスを追加
 ⇒ノード名でアクセスできるようになる
// vi /etc/hosts
// 172.31.43.201 node01
// 172.31.43.187 node02
// ssh ec2-user@node01 -i ansibleWS.pem
-------------------------------------------------


-------------------------------------------------
自作 inventory ファイルを作成する場合
// cat > inventory.txt
// target1 ansible_host=192.168.100.9 ansible_ssh_pass=myPassword
// target2 ansible_host=192.168.100.11 ansible_ssh_pass=myPassword
//  →そしてCtrl + C
-------------------------------------------------


inventory を利用して ping を打つ
// ansible target* -m ping -i inventory.txt
// target1 →エイリアス：ansible_host= →FQDN を指定
// ansible で、このモジュールに、このコマンドを打つ。モジュールのアドはこのファイルを参照
// -m →モジュール
// -i ホストリストが記載された インベントリファイルを指定
-------------------------------------------------
