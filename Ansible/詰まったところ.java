▼ apache が スタートしてくれない
-------------------------------------------------
apache2 のステータスが dead のまま
// systemctl start apache2
// systemctl status apache2

nginx が入ってて、そいつが動いていた
// dpkg -l | grep nginx

nginx を ストップしてから apache2 をスタートしたら動いた
// systemctl stop nginx
-------------------------------------------------




▼ apache2 のステータスチェックが成功しない
-------------------------------------------------

対象ホストの apache2 のステータスは running なのに、until が終わらない
// until: result.stdout.find("active(running)") != -1

ただのタイポ。というか、文字列を律儀に探しているとは思わなかった
// find("active(running)") ⇒  active (running)    //スペースが抜けてた
-------------------------------------------------



▼ wordpress Role 内の defaults/main.yml に変数定義していないのに、参照しようとしてた
-------------------------------------------------
TASK [wordpress : Update WordPress config file]
fatal: [nodeUbuntu01]: FAILED! => {"msg": "'wp_mysql_db' is undefined"}
-------------------------------------------------


▼ タイポ
-------------------------------------------------
// 誤
- {'regexp': "define\\('DB_NAME', '(.)+'\\)",
     'line': "define('DB_NAME', '{{ wp_mysql_db}}');"}

// 正
- {'regexp': "define\\('DB_NAME', '(.)+'\\);",
     'line': "define('DB_NAME', '{{wp_mysql_db}}');"}
-------------------------------------------------



▼ roles/some_server/tasks/some.yml で、ホストを指定していた
-------------------------------------------------
単体の playbook として使った時は、host 指定していても問題なかったが、
task ファイルとして扱う際、

タスクが見つかりませんエラーが出た
 ⇒ fatal: [host02]: FAILED! => {"reason": "no action detected in task. "

下記ホスト、タスクの指定部分が原因。取り除いて解決
// - hosts: ansible_controller
//   tasks:
    - name: Installing software-properties-common
      apt: name=software-properties-common state=installed update_cache=yes
-------------------------------------------------








▼ yum と apt では state の書き方が違っていた
-------------------------------------------------
下記エラー
  "value of state must be one of: absent,forcereinstall,present,latest, got: installed"}
    yum: name=* state=installed//間違い
    yum: name=* state=latest//これならOK
-------------------------------------------------


▼ ansible インストール時に ansible.cfg が生成されていなかった
-------------------------------------------------
epel-release を引数にインストールする必要があった
// yum install epel-release
// yum install ansible --enablerepo=epel
epel-release のインストール
// - name: Installing EPEL repository
//   yum: name=epel-release state=present
//   when: ansible_distribution == "Amazon"
enablerepo を指定した上でのインストール
// - name: Installing ansible for RedHat
//   yum: name=ansible state=present enablerepo=epel
//   when: ansible_distribution == "Amazon"
-------------------------------------------------














▼ 条件分岐 when のインデントが間違っていた
-------------------------------------------------
// - name: Reboot the host
  include_role:
    // name: common
    // tasks_from: reboot
    下記だと、include_role module 内の項目になってしまう
    when: ansible_distribution == "Amazon"
下記のように、include_role と同じレベルのインデントにしましょう
  when: ansible_distribution == "Amazon"
-------------------------------------------------




▼ Amazon Linux のホスト名変更は、 Debian 系の ubuntu と違っていた
-------------------------------------------------
/etc/sysconfig/network を修正
// sudo vi /etc/sysconfig/network
// 下記を変更
// HOSTNAME=host04

再起動
-------------------------------------------------

RHEL のホスト名変更
-------------------------------------------------
/etc/hosts 及び /etc/hostname を修正

/etc/sysconfig/network
下記追記
HOSTNAME=persistent_host_name
-------------------------------------------------

















▼ Playbook を走らせる際、対象ホストの情報がうまく渡せていなかった

対象ホストの指定
-------------------------------------------------
▼ roles 構成外のプレイブック
-------------------------------------------------
yml ファイル内のターゲットセクションでホストを指定する必要がある
// - hosts: host04

ホスト名に変数を割り当てるようにして、 引数として渡せるようにもできる
下記は yml ファイル内の内容
// - host: "{{ target_host }}"
下記は実際のアドホックなコード
// ansible-playbook magic_vars_test.yml -e "target_host=host04"

-------------------------------------------------





引数間違い
-------------------------------------------------
// - r
上記はスペースあっていい
// --recursive
上記はスペースあっちゃだめ

-------------------------------------------------







対象プレイブック 及び 対象ホストの適用フロー
-------------------------------------------------
他の playbook を 招き入れる大元の playbook
// # master playbook site.yml
// - import_playbook: ansible_hosts.yml

参照される playbook
// # ansible_hosts.yml
★ 対象とするホスト(グループ)? の指定が必要
// - hosts: ansible_controller
//   roles:
//     - ansible_hosts

対象とするホストの情報はインベントリから
デフォルト
// /etc/ansible/hosts
引数で指定
// ansible-playbook set_hostnames.yml -i staging
個別の インベントリ の中身
指定したインベントリ内に、対象ホスト(グループ)? の情報がなければ情報は返ってこない
そもそも、個別インベントリを用意する理由は、
// [web_server] とか [db_server] といった粒度のグループを、
// staging と production とで分けるためなのであれば、
// そういう用途がない限り、デフォルトのインベントリを使ったほうがいいのかもしれん

// [ubuntu]
// host02
//
// [ec2]
// host03
-------------------------------------------------
