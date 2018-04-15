おっしゃってることがわかりかねます。

お休みのところ失礼致しました。

20分後に出頭せよとのことです。

まことにありがとう。

さて。では少将閣下と会ってくる。

言わんとすることはわかる。s

率直に申し上げますが

謹んで申し上げますが

ご諮問に対してはこれ以上のことを申し上げうる立場にないと指向いたします。

ご理解いただけましたか。ありがとうございます。

閣下、お言葉ですが

しかし閣下ひとつだけよろしいでしょうか

理解致しました。
では私はこれにて。

それで会議のほうはつつがなく？

残念ながらあまり良くない知らせのようです
申し訳ありませんが、召喚はこれにて中座させていただきたく
無礼のほどご容赦いただきたく思います。では。
-------------------------------------------------


https://www.lisenet.com/2014/create-and-attach-a-second-elastic-network-interface-with-eip-to-ec2-vpc-instance/
-------------------------------------------------
・AWSサービス は 単一の NI に 複数の プライベート IP を付与できるようになった。
EIP は 単一の NI を使うことで、複数の プライベート IP と関連付けることができる。

・Goe says
ENI を一つ削除して、インスタンスを再起動してみる。
NIC を削除すると、OSK追加 が起動時にコンフィグを再評価する。



-------------------------------------------------
# cat /etc/network/interfaces.d/eth0.cfg
# The primary network interface
auto eth0
iface eth0 inet dhcp
-------------------------------------------------
# cat /etc/network/interfaces.d/eth1.cfg
# The secondary network interface
auto eth1
iface eth1 inet static
 address 10.20.0.11
 netmask 255.255.255.0
 up ip route add default via 10.20.0.1 dev eth1 table out
 up ip rule add from 10.20.0.11/32 table out
 up ip rule add to 10.20.0.11/32 table out
 up ip route flush cache
-------------------------------------------------
※コンフィグに、 ip rule を付け加えないと、再起動時に一貫性を保てないとのこと
