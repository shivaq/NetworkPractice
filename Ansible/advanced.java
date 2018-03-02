マジック変数
-------------------------------------------------

▼ inventory_hostname
// ansible の host ファイルに設定されたホスト名

▼ inventory_hostname_short
inventory_hostname のうち、最初のピリオドまでの部分







▼ hostvars // 他のノードの変数を取得
-------------------------------------------------
test.example.com というホストの変数 ansible_distribution を取得
// {{ hostvars['test.example.com']['ansible_distribution'] }}
-------------------------------------------------


▼ group_names // 現在のホストが属するすべてのグループ
// templates 内で、 jinja2 を使って使用する
-------------------------------------------------


{% if 'webserver' in group_names %}
   # some part of a configuration file that only applies to webservers
{% endif %}


#host_key_checking = False
