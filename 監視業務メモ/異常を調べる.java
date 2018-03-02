
▼ ネットワークの状態を見る
-------------------------------------------------
外部との通信ができない場合
// デフォルトゲートウェイが怪しい。DG は外部との出入り口になる通路。通常はルータがこの機能を提供。VM の場合は ホストがルータ役。
デフォルトゲートウェイを確認
// route -n
// デフォルトゲートウェイ 複数あったら、コンフリクトが発生するので一つにする

-------------------------------------------------
アクティブなすべての インターフェイスを見る
// ifconfig


非アクティブも含めて見る
// ifconfig -a
// UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1  このラインに UP と出てるとアップ
特定の インターフェイスの 速度や link 状態を見る
// ethtool enp0s8
// Link detected: yes // リンクはOK

フレームエラーなどの数を見る
// ifconfig
// RX packets:1026 errors:0 dropped:0 overruns:0 frame:0
// TX packets:1037 errors:0 dropped:0 overruns:0 carrier:0
// // ethtool -S enp0s8 // こっちのほうがより詳細

ARP テーブルを見る
// yasuaki@yasuaki-VirtualBox:~$ arp -a
// ? (10.0.2.2) at 52:54:00:12:35:02 [ether] on enp0s3
-------------------------------------------------











I/O っでブロックしている接続先を特定する
-------------------------------------------------
怪しいプロセスに当たりをつける
// ps aux

怪しいプロセスを strace する
// sudo strace -p 12690
// システムコールとシグナルを監視して、プログラムとOSとのやり取りを見る











▼ パフォーマンスを見る
-------------------------------------------------
▼ top
%CPU// マルチコアだと、100を超える
// us: user cpu time (or) % CPU time spent in user space
// sy: system cpu time (or) % CPU time spent in kernel space
// ni: user nice cpu time (or) % CPU time spent on low priority processes
// id: idle cpu time (or) % CPU time spent idle
// wa: io wait cpu time (or) % CPU time spent in wait (on disk)
// hi: hardware irq (or) % CPU time spent servicing/handling hardware interrupts
// si: software irq (or) % CPU time spent servicing/handling software interrupts
// st: steal time - - % CPU time in involuntary wait by virtual cpu while hypervisor is servicing another processor (or) % CPU time stolen from a virtual machine

load average（LA）
// サーバや組込みシステムで、Overload 状態ならば、リソースを食っているプロセスをなんとかせんといかん。
// CPU のリソースを使っているもしくは待っているプロセスがあると LAに 1 追加される
// ディスクの I/O を待っているプロセスがある場合もカウントする。

load average: 1.05, 0.70, 5.09 を翻訳// CPU は一つと仮定する
// Last 1min, 5min, 15min
// 1.05 →CPU は 5% の Overload. 平均0.05 個のプロセスが CPU 待ち。
// 0.70 →CPU は 30% の時間アイドル状態
// 5.09 →CPU は409% の Overload。 平均4.09 個のプロセスがCPU待ち。

CPU 数
///proc/cpuinfo の processor で始まる行の 3 つ目のColumn のうち、最後の1つを出力する。
///プロセッサ番号は 0 から始まるため、出力された数値 + 1 が CPU 数
cat /proc/cpuinfo | awk '/^processor/{print $3}' | tail -1
// /proc/cpuinfo の processor で始まる行数をカウント
grep -c ^processor /proc/cpuinfo


RAM// ・よく使うファイルを、HD だけでなく RAM にも配置して、アクセスしやすくしている
// ・RAM にファイルが多いと、Stack が大きくなると、ソート対象のデータが増え、パフォーマンスが下がる
// ・free より top のメモリ情報のほうが詳細。
// ・通常 Swap の値は低いはず。もし高ければ、RAM を増設するか、走らせるプログラムを減らすべき。
Swap// ・メモリの情報のうち、優先度の低いものをHDにコピーし、必要に応じてHDからメモリに移して復元できるようにしておく。
// メモリ/HD 間のデータやり取りにはコストがかかるため、Swap の値が大きいと、パフォーマンスが下がる
PR// カーネルからみたプロセスのプライオリティ。リアルタイムなプロセスの場合は、RT とだけ表示される。
NI// ユーザーがコントロールできる、PR のオフセット。最高が -20 最低が +19 デフォルトが 0。
// ランチタイムなどに一次的にプロセスのPRを上げたい場合 → nice -n -20 process_name/id
VIRT// virtual size of a process。使用されているメモリや、HDからマップされたメモリなどなどのSUM。
// そのプログラムが現在アクセス可能なメモリを表す。
RES// resident size。プロセスが消費している物理メモリの容量。%MEM 列に対応している。
SHR// VIRT のうち、どれくらいが共有可能なメモリやライブラリかどうかを表す。
// ライブラリに関しては、ライブラリ全体がロードされている事を表さない。

vmstat 10// 10秒毎の、CPU,IO,プロセス、メモリ使用率のスナップショットを出力し続ける
// process r →実行待ちプロセス b →スリープ状態プロセス。r があるということは、どこかにボトルネックがあるということ。
// memory virtual, free, buffer, cache メモリ
// swap スワップしてるメモリの数の input/output 別
// io →ブロックデバイスから送られてくる/送られる ブロック数
// us ユーザーのタスク/コードが走った回数
// sy カーネルやシステムのコードが走った回数
// wa IO待ち
// st ゲストの仮想CPUが、ハイパーバイザーの（他の仮想CPUに時間を割いている）リアルCPUが空くのを待った時間。100% だと全く動けていない状態。
// ガイドラインとして、%st が 10% を超える状態が 20分を超えたら、その VM は遅い状態になっていると思われる。
// 解決策1.インスタンスをシャットダウンして別の物理サーバに移す 2.CPU リソースを増やす 3.プロバイダに連絡。そのホストは oversold かもしれん。
// すべての Virtual Serverで、 %st が増えている場合 →あなたのVM が利用可能な CPU リソースを増やすべき。
// 一部の Virtual Server でのみ、 %st が増えている →そのサーバだけが oversold 状態かもしれない。VM を別の物理サーバに移しましょう。
// Netｆlix では、steal がしきい値を超えると、その VM をシャットダウンして他の 物理サーバ上で立ち上げるとのこと。
// http://blog.scoutapp.com/articles/2013/07/25/understanding-cpu-steal-time-when-should-you-be-worried
// しかし、VBox や VMware はこの値をサポートしておらず、 Xen や KVM のみ対応しているとのこと。
Buffer// FIFO。RAM のうち、ディスクの cache に使われている割合。
// メモリ内の特定のI/Oブロック。
// キャッシュだけれどメタデータ用のキャッシュ。ページキャッシュではない（ファイルデータではない）。
// ディレクトリ内に何があるか、ファイルのパーミッションは何か。対象のブロックで、どのメモリのデータが読み書きされるか。

cache// Least Recently Used.キャッシュしたファイルデータそのもの。
// Cached メモリは事実上フリー。プログラムがメモリを必要とした時には、すぐに置き換えられる。
// Linux はRAMが未使用なのはもったいないので、とりあえずキャッシュしておくという方針。

ページキャッシュ
// Linux はI/Oをページキャッシュを通じて行う。
// 書き込み時 →HDの書き込み対象ファイル に対応するページキャッシュ内のページに書き込んで、ディスクに書き写す感じ。
// 読み込み時 →対応するページキャッシュのデータを返す。
// ページキャッシュにデータがなければ（未だメモリになければ）キャッシュとしてページキャッシュに書き写す感じ。

sar// -n ALL ネットワーク, -m メモリ,
iotop// HD のinput/output を見ることができる top の・ようなもの。
// ファイルの書き込みや、find コマンドなどで IO が多い様子などがわかる。
// プリインストールされていない。
iostat// CPU, Device, Network の使用率。-c,-d,-h   -k,-m
tps →transfer per seconds
scd →CDROM ドライバ
sda0 →sdは、もともと SCSIデバイスを表していたが、現在では単にデータを保持できるデバイスを表す）
 →a は通し番号みたいなもの。abcからAaAzまで。0 はパーティション番号。
-------------------------------------------------
