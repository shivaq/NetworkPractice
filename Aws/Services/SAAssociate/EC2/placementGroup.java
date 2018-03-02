Placement Group とは
-------------------------------------------------
1つの AZ 内にあるインスタンスの、ロジカルなグループ

何に使える？
グリッド・コンピューティングや Hadoop など。
10 Gbps の low latency かつ high throughput なネットワークになる。

制約
・AZ をまたがることはできない
・プレースメントグループ名 は AWSアカウント内で ユニークでなければならない
・ローンチできるインスタンスタイプに制限あり（Compute, GPU, Memory, Storage Optimized)
・プレースメントグループはマージできない
・既存のインスタンスを、プレースメントグループに後から含める事はできない
・解決策 ⇒既存のインスタンスから AMI を作成 ⇒AMI からインスタンスを作成し、グループに組み入れる

推奨
・同一プレースメントグループ内では、同一タイプ、サイズを使うことを AWS は推奨している
-------------------------------------------------
