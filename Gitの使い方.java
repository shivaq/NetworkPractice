▼ Git の基本や コラボレート方法、マージコンフリクトについて
-------------------------------------------------
▼ エラー
-------------------------------------------------
warning: not deleting branch 'add_activity' that is not yet merged to
       'refs/remotes/origin/add_activity', even though it is merged to HEAD.
原因 →ローカルでコミットしたけど、origin の方にコミットせずに削除しようとしている。
-------------------------------------------------
Your local changes to the following files would be overwritten by merge:
-------------------------------------------------
feature branch でコミット → develop へチェックアウトして、マージしようとしたら出た。
no change だけど、develop で delete したファイルがあるので、それが反映されていないとのこと。
git status で、delete されたファイルがこれこれ、みたいなのがある。
各変更を選択して、
git add 変更内容行
これでマージできるようになった// git commit が、 Ctrl + k をして no changes ダイアログがでた時に成されていたからかもしれん。
-------------------------------------------------
▼ コミットメッセージ
-------------------------------------------------
・なぜそのコードが書かれたのか、なぜそこに書かれたのかという文脈が、後で見てもわかるようにすることが重要
・コミットのコメントに一貫性をもたせる
-------------------------------------------------
▼コミットメッセージスタイルガイド
-------------------------------------------------
// メッセージ構造
type: subject
body
footer
-------------------------------------------------
// 各部分解説-The Type
feat: 新規feature
fix: バグをfixした
docs: ドキュメントを修正した
style:フォーマットを直した。セミコロン追加したなど。 ※code変更はなし。
refactor:プロダクションcodeをリファクタリングした
test:テスト追加、テストをリファクタリング。※プロダクションcode変更はなし
chore:ビルドタスク、パッケージマネージャ コンフィグなどをアップデート。 ※プロダクションcode変更はなし
-------------------------------------------------
//The Subject
サブジェクトは 50文字以下で、ピリオドはつけない。
コマンドであるかのように、コミットが何をするか、と書く。何をしたかではなく。
例）change を使う。changed や changes ではなく。
-------------------------------------------------
//The Body
オプション。ちょっとした文脈の説明などが必要な場合に限り、body を記載。
このコミットが解決した問題を説明。
what 及び why この変更を行ったかを記載。 （どのように、はcodeが語るので不要）
変更による副作用や、直感にそぐわない結果があれば、ここに記載。
- 箇条書きは、スペースアンドハイフンで。
- 箇条書きの間は空行を入れる
タイトルとボディの間に空行必須。
行の長さは 72文字以内
-------------------------------------------------
// The Footer
フッターはオプショナル。issue tracker を使っている場合、記載。
-------------------------------------------------
▼ Commit の頻度について
-------------------------------------------------
▼ロジカルな変更ごとにコミットするのがおすすめ
-------------------------------------------------
タイポを修正後、他の箇所のバグを修正したとする。
⇒各修正時にコミットすべし。
⇒各コミットが、1つの目的のためのFixとなるため、わかりやすい。
- 1つのfeature追加に取り掛かっている。1週間して、ようやくコミット ⇒コミット期間が大きすぎ
- Readme に３っつのタイプミス。それぞれにコミット ⇒細かすぎ。タイポ修正まとめてコミット一回でいい。そもそも、Readme 間違っててもバグにはならない。
- 1つのfeature追加に取り掛かっている。1時間して、ようやくコミット ⇒適切なサイズ。もう少し細かくコミットしてもいい。
-------------------------------------------------
- こまめなcommitsがおすすめ。
差異が大きくなると、わかりにくくなるから。
自身の見積もり精度やタスク管理能力向上
複数タスクが混在したコミットは悪
コミットとタグの相性UP
Pivotal などの外部サービスとの連携が容易
履歴を参照する際のコスト低下(log, reset, bisect, rebase時など)
自分や他人がみてもcommit内容の把握が容易
- 細かすぎるコミットに注意
履歴が読みづらくなるから。
※どのファイルも更新していない場合、新規コミットは作成できない。
コミットAを作成し、コミットBを作成した時点でバグが見つかり、コミットAに戻ってそのまま変更無しでコミット
結果、コミットCができるも、C と A とはファイル内容は同じ、ということはあり得る。
- コミットはブランチのことを何も知らない。ブランチの事を知っているのはブランチだけ。
-------------------------------------------------
▼ コミット戦略
例えば。。。
-------------------------------------------------
1.ワーキングエリアで、コーディングを進める。//これ基本
2-a.多少なりとも変更した部分をレビューする。git diff some.txt
2-a-2.OK なら、とりあえずその部分だけでもステージする。git add ○○
git diff ○○で、         ワーキングとステージングエリアとの diff
git diff --staged ○○で、ステージングエリアとレポジトリとの diff
2-a.少しでも、まとまりのある範囲のコーディングが進んだと感じたら、git add
3.ステージングエリアで、何をするんだ？この場合。ただ、コミットするだけか。
-------------------------------------------------
例えば。。。
-------------------------------------------------
1.ワーキングエリアで、A のコーディングを進める。//これ基本
2.別のこと B を行うことになる。
3.ステージングエリアに A を上げる。コミットする。//この際、中途半端にコミットはしない、よね？
1. に戻り、B を進める。
効用：現在やっていることが何なのか、セパレートさせる習慣ができて、頭がスッキリする？
-------------------------------------------------
▼ブランチたくさん作る派※まずは作る派を試してみたい
-------------------------------------------------
どうマージするかが、課題になりそうだな。
- 異なるピースに移るとき。
違ったことに手を付ける時。
新しいfeatureに移る時
バグをfixする時
- コンテキストをスイッチ 各種 Work を compartmentalize（区画わけ）する
 -> feature に取り掛かかる仕事から離れる
 -> バグFix するための場所に戻る
- 今のworkに飽きたら、コンテキストスイッチできる。
- Unreachability
- ラベルはこまめに付けましょうってことになるかな。
あるブランチから別のブランチの枝先までは到達できない。
checkout で過去に戻った上でコミットして、ラベルをつけず、マスターブランチにスイッチすると、
そのラベルを付けていないコミットには、ID をメモでもしていない限り戻ることはできない。
-------------------------------------------------
▼ブランチあんまり作らない派 ※彼は優秀そうな匂いがしていた
-------------------------------------------------
// ※Google シニアソフトウェアエンジニアのブランチポリシー
- なるべくブランチは少数にする。可能なら1つのみ。
出来る限りたくさんのコミットをすることが、私にとって最も重要なことです。
見返す必要がある以上のコミットが出来上がる。
けど、コミットの用法は、Undo するためだけではなく、どうしてそれを行ったかを振り返るためでもある。
-------------------------------------------------
▼ 失敗例
-------------------------------------------------
プレースホルダーとしてアダルトチャットの電番入れてたのに、更新するのを忘れてた
自分で書いたコードが、何を目的として書いたものなのか覚えていない
-------------------------------------------------
▼Git のエラーコメントについて
-------------------------------------------------
detached HEAD
⇒HEAD が最新のコミット。古いコミットに移動してそこから開発を始めると、頭がデタッチされるよ、
というお知らせ。
-------------------------------------------------
▼diff 結果の見方
-------------------------------------------------
//比較対象A
1のここを消す
2
2と3 の間のここを消す
3
4
5
-------------------------------------------------
// 比較対象B
2
3
4
4と5の間のここに足す1行目
4と5の間のここに足す2行目
5
6行目に文字を書き足した
-------------------------------------------------
// diff 結果
@@ -1,6 +1,9 @@
-1のここを消す
2
-2と3 の間のここを消す
+
3
4
-5
\ No newline at end of file
+4と5の間のここに足す1行目
+4と5の間のここに足す2行目
+
+5
+6行目に文字を書き足した
\ No newline at end of file
-------------------------------------------------
▼コマンドたち
-------------------------------------------------
▼コミット時の流れ
-------------------------------------------------
git status
git log//このログでは、下にある方が古いバージョン
//バージョン移動したい場合はこれ
git checkout 25ede836903881848fea811df5b687b59d962da3
- 修正を実施 ->修正したら、diff them add them commit them.
git diff //※ 引数なし
git add somefile.txt//file をworking directory からステージングエリアに配置し、track 開始
git diff --staged//git commit 前に、コミットしたいファイルかどうかをダブルチェックするのに使える
git commit
-------------------------------------------------
- 設定系
git config --global core.autocrlf true//Unix 系の改行 LF を、Windows が CRLF に変換しないように設定する。
//他のコンピュータにあるレポジトリの"ヒストリーを丸々"ダウンロード ->ローカルから見たリモートポイントも自動的に設定される。
git clone https://github.com/udacity/asteroids.git
git init//そのディレクトリに.git を作成し、新規Gitレポジトリにする
-------------------------------------------------
- 内容確認系
git log//このログでは、下にある方が古いバージョン
git log --graph --oneline master easy-mode//ブランチの分岐をビジュアル化
git diff 4035769377cce96a88d5c1167079e12f30492391 25ede836903881848fea811df5b687b59d962da3
//注意!:スクロールはホイールではできません。
git diff //※ 引数なし
git diff --staged//git commit 前に、コミットしたいファイルかどうかをダブルチェックするのに使える
git status
git show commit_id//親コミットと比較する
-------------------------------------------------
- エリア移行系
git add somefile.txt//file をworking directory からステージングエリアに配置し、track 開始
git reset somefile.txt//ステージングエリアに配置したファイルを、コミット対象から外す
git reset --hard//！！要注意コマンド！！ コミット後は、元戻し可能だが、このコマンドは元戻し不可能。    ワーキングからもステージングからも、全ての変更点をリセットする
git checkout 25ede836903881848fea811df5b687b59d962da3
-------------------------------------------------
- コミット！
git commit
- ブランチ系
git branch//ブランチリスト表示
git branch easy-mode//ブランチ作成
git checkout master
git checkout easy-mode//ブランチをスイッチ
git checkout -b new_branch_name//新規ブランチを作成し、かつ、そのブランチへチェックアウトする
-------------------------------------------------
- マージ系
git merge branch1 branch2//現在 checkout 先のブランチのラベルが、コミット後に残るラベルとなる。 ※引数の順番は関係ない
git show commit_id//親コミットと比較する
git branch -d coins//ブランチの、ラベルを消す。コミットは消えずに残る。ラベル消えても、マージ先から遡れる。
-------------------------------------------------
▼ リポジトリの見通しを良くするために使用済みbranchは削除：
-------------------------------------------------
$ git branch -d unused_branch
$ git push origin :unused_branch
-------------------------------------------------
▼ リモートのブランチを、ローカルにチェックアウト
-------------------------------------------------
git checkout -b other_branch origin/other_branch
//ローカルのブランチ名を指定する必要がある
-------------------------------------------------
チートシート
https://services.github.com/kit/downloads/github-git-cheat-sheet.pdf
-------------------------------------------------
▼ マージ
▼ merge 甲 ブランチ into the master ブランチ
-------------------------------------------------
▼ マージの結果、マージされるもの
-------------------------------------------------
- マージ結果のブランチは、双方のブランチを遡ることができる。
A ブランチのティップを マスターブランチのティップにマージすると、
結果として2つのコミットから1つのコミットとなったマスターには、
マスター自身の更新内容に加え、A の更新内容も反映される。
-------------------------------------------------
▼元のブランチはもう用済み。マスターさえいればよい。
-------------------------------------------------
親情報を2つ持つこととなり、
マスターから元のラインにも、A のラインにもアクセスできる。
よってマージ元 のうち A の方のブランチは不要となる。
A のブランチそのものというより、A の方の ラベルが不要となると言い換えることもできる。
-------------------------------------------------
▼用済みじゃないのに 誤ってブランチ削除しないよう注意。
▼ブランチ ガーベージコレクション
-------------------------------------------------
ブランチが削除され、現存するブランチからはアクセスできないコミットが残されると、
ブランチのガーベージコレクションが定期的に働き、処理していく。//git gc で、手動でGC できる。
-------------------------------------------------
▼マージで異なるブランチの時系列が単純合体した結果
-------------------------------------------------
隣り合うコミットを比較しても、親子関係のコミットとは限らないため、
すなわち変化前、変化後の比較とはならなくなる。
そんな場合の変化確認は下記コマンド
git show commit_id
-------------------------------------------------
▼マージコンフリクト
-------------------------------------------------
▼ HEAD の内容が ======= の上にある内容、そして iss53 ブランチの内容が下の部分であるということ。
-------------------------------------------------
<<<<<<< HEAD:index.html
<div id="footer">contact : email.support@github.com</div>
=======
<div id="footer">
please contact us at support@github.com
</div>
>>>>>>> iss53:index.html
-------------------------------------------------
▼ すべてのコンフリクトを解決したら
-------------------------------------------------
git add コンフリクトを解消したファイル名1
git add コンフリクトを解消したファイル名2
(直したファイル数分繰り返す)
git rebase --continue
git push origin master
-------------------------------------------------
同じ部分をmodify しているブランチをマージする場合に下記メッセージが出力される
-------------------------------------------------
CONFLICT (content): Merge conflict in game.js
-------------------------------------------------
下記ファイルをマージする場合、B 系のパートをマージしていいか自動判断できない。
// オリジナル     A B   D
山田エディション  A B'  D
'小池エディション A B'' D
加えて、B'' たちが、B をmodify した結果なのか、B を丸々消して新たに書いたラインなのかもわからない。
- 別のケース
E と F がそれぞれ別のブランチで作成され、名前は違うけれど機能は一緒だとする。
マージして残すのはどちらか一方でよい。みたいな時
-------------------------------------------------
▼マージコンフリクト 対策
-------------------------------------------------
<<< とかで検索すると、
Git が下記のようにコンフリクト箇所を、該当ファイルに記載している
A.<<< と ||| の間が HEAD のパート
B.||| と === の間が共通のデータ元部分（未修正状態）
C>=== と >>> の間が master のパート
-------------------------------------------------
▼マージ方針を協議
-------------------------------------------------
A と B との違い及び B と C との違いから、互いの修正意図を理解した上で
A パートをやった人と B パートをやった人とで話して、どう扱うかを決めるのが通常。
-------------------------------------------------
<<<<<<< HEAD
    // break into fragments
    for (var i = 0; i < 2; i++) {
      var roid = $.extend(true, {}, this);
      roid.vel.x = Math.random() * 6 - 3;
      roid.vel.y = Math.random() * 6 - 3;
      if (Math.random() > 0.5) {
        roid.points.reverse();
      }
      roid.vel.rot = Math.random() * 2 - 1;
      roid.move(roid.scale * 3); // give them a little push
      Game.sprites.push(roid);
    }
||||||| merged common ancestors
    // break into fragments
    for (var i = 0; i < 3; i++) {
      var roid = $.extend(true, {}, this);
      roid.vel.x = Math.random() * 6 - 3;
      roid.vel.y = Math.random() * 6 - 3;
      if (Math.random() > 0.5) {
        roid.points.reverse();
      }
      roid.vel.rot = Math.random() * 2 - 1;
      roid.move(roid.scale * 3); // give them a little push
      Game.sprites.push(roid);
    }
=======
    this.breakIntoFragments();
>>>>>>> master
-------------------------------------------------
▼マージコンフリクト解決結果を Git に反映させる
-------------------------------------------------
1.対象部分を修正して保存
2.git status で確認すると、下記のように both が modified されたと出る
both modified:   game.js
3.git add game.js  ->git status
下記のように出力される
All conflicts fixed but you are still merging.
(use "git commit" to conclude merge)
4.git commit
後のわかりやすさのためにも、
コメントアウトされているコンフリクト対象の情報をコメントにいれるのがおすすめ
-------------------------------------------------
- fast-forward Merges
a が b の先祖である2つのコミットで、b が a にマージされる時、//一方がもう一方からreachable な時
そもそもa が b のログに含まれているわけなので、ラベルのみが a に更新され、他に変更点はない。
-------------------------------------------------
▼変更対象のレポジトリを、再度 GitHub のオリジナルレポジトリと同期する
-------------------------------------------------
git pull origin master
git merge origin someBranchForTryChange
コンフリクトがあれば、修正 →add →コミット
git push origin someBranchForTryChange
※すでにプルリクエスト済みのものを、再同期した場合、
プルリクエストのコミットに、再同期時のマージのコミットが追加されている。
※コンフリクトをマージした場合、もう一方のプルリクエストをした人に、
そのことを伝えるためにも、自分のGitHub のプルリクエストのconversation タブのコメントに、
please take another look とかコメントを入れると、そのコメントが相手に通知される。たぶん。
-------------------------------------------------
▼ index
例えば。。。大きな変更を、複数のコミットに事後分割ができるらしい？？？
-------------------------------------------------
- index は、変更点のエリアを保持している。そのエリアが git commit 対象となる。
commit は現在のワークツリーをレポジトリに入れ込むだけの処理ではない。
indexが、現在のワークツリーの "どの部分を" レポジトリに入れ込むかをコントロール可能にしている。
- 大きな変更を複数のコミットに分割する助けになる
複数のファイルと少しの異なるサブタスクを含む大きな変更に取り組んでいるとする。
で、まだコミットするために分割する状況ではないとする。
で、変更点がテストされ、ワークすることが確認できた段階に至り、
分割をしていくことになる。各コミットが、1つの変更点に焦点を当てているよう、適切に行う必要がある。
-------------------------------------------------
■■Git の Hub とのあれこれ
▼Github との間でレポジトリをやりとり
-------------------------------------------------
// github で新規レポジトリを作成すると出て来る URL を使用する
// origin は、任意でつけるリモート名
git remote add origin https://github.com/shivaq/reflections.git
git remote -v//リモート作成を確認
origin  https://github.com/shivaq/reflections.git (fetch)
origin  https://github.com/shivaq/reflections.git (push)
// $1:送りたいリモート $2：送りたいブランチ
git push origin master
git pull origin master//マージ先のtip of branch にチェックアウトしておくこと！
//pull の結果、ローカルマスターだけでなく、working directory, staging area もアップデートされる。
//上記は右記2つのコマンドを同時に実行することと同意。git fetch origin, git merge master origin/master
//origin/master は、最初にgit clone したときにできたブランチ。
//その後のコミットは、GitHub でのヒストリとのずれを吸収するために、origin/master とは別の master なんだと思う。
-------------------------------------------------
▼ GitHub でコラボレート
▼ブランチとコラボレーション
-------------------------------------------------
- ブランチわけした後、個々のブランチ内で git checkout できる。
- remote branch ->他の人が作ったブランチ
注意! remote branch は、git branch しただけではリストに表示されない。
一度 git checkout someones_branch をした後でようやく、そのブランチがリストに表示される
-------------------------------------------------
▼GitHub にローカルでの変更をプッシュする際のフロー
-------------------------------------------------
状況：GitHub でコラボレートしているプロジェクトがある。
概要：ローカルで変更し、自身のフォークにプッシュし、プルリクエストをする →他者が見られる状況になる
目的：ローカルで行った変更を、GitHub のオリジナルレポジトリにマージしてもらうようリクエストする
※ってことかな。そもそも GitHub にプッシュするって事は、基本的にはオリジナルへのマージを想定しているからか。
-------------------------------------------------
// ローカルマスターにて、セパレートしたブランチに変更を加える。
1.ローカルでセパレートブランチを作成し、そこにチェックアウトする。//マスターは残しておく
1-1.そこで変更を実施
1-2.add, commit →git push origin some_separated_branch//$2 は変更を加えたブランチ。マスターとは限らない。
（GitHub で変更がシェアされ、他の人も見られる状態）
（GitHub マスターブランチにはなっていない。マスターは壊れない。）
//GitHub のプロジェクト管理者みたいな人に、オリジナルレポジトリにPull（マージ）してくれるようリクエスト？
2.GitHub で、base fork を確認した上で、Compare & pull request を押下（push したてほやほやの場合）
2.Compare & pull request がない場合、
→セパレートブランチにチェックアウトしてpullリクエストを押下 →ベースフォークを確認し、create pull request を押下
※GitHub では、マージ先がデフォルトではオリジナルレポジトリになっている。
自分の GitHub のフォークのマスターブランチにマージする場合は、
Edit を押下後、ベースフォークを自身のものに変更してから行う。
3.pull リクエストの結果、他者がそれを見たり、コメントを残したりできる状況になる
4.マージに要修正点がある場合、変更者にインラインコメントし、//該当箇所に＋ボタンが現れるので、押下するとピンポイントでコメントできる
修正を要請する。
コメントは、そのコミットのオーナーにメールで送られる
4-1.変更者がローカルで修正し、プッシュし直すと、
プルリクエストが更新される。元となるコミットのコメントに返信というかたちでコミットが追記される。
5.マージにコンフリクトがなければ、Merge pull request ボタンが押下可能状態になる。
-------------------------------------------------
▼GitHub における pullリクエスト間のマージコンフリクトへの対応
-------------------------------------------------
2つのプルリクエストがあるとして、
一方を先にマージしてもよさそうなら、マージする。
で、そのプルリクエストのブランチが、プルリクエストのためだけのブランチなら、
マージしてすぐにブランチ削除するのは pretty common。
▼GitHub のレポジトリ状態とローカルの状態とを同期させる
-------------------------------------------------
git pull origin master
-------------------------------------------------
▼ レポジトリを変更し、GitHub にアップする
-------------------------------------------------
git pull origin master
git branch someBranchForTryChange
git checkout someBranchForTryChange
変更実施 →add →commit
git push origin someBranchForTryChange
GitHub にてプルリクエスト
-------------------------------------------------
▼他者が行った変更点との間でアップトゥーデートする。
-------------------------------------------------
1.ローカルのマスターブランチにPullしてくる。
2.ローカルマスターを、セパレートブランチにマージする。
3.セパレートブランチを、マスターにマージする前に（ローカルでも、GitHub でも）
セパレートブランチをリモートにプッシュする。
-------------------------------------------------
- なぜ上記のようなプロセスを取るのか
マージには取捨選択が伴う。複数間でコラボレートする場合は、
その取捨選択の判断理由もシェアし、合意なり意思決定なりをした上で、
マスターを更新したいから。
-------------------------------------------------
▼ GitHub でのコラボレートでの、マージの扱い
-------------------------------------------------
変更加えるときは、
オリジナルレポジトリ(GitHub マスター)をローカルにPullして、
それをもとにセパレートブランチを作り、
そのセパレートブランチで修正して、
そのセパレートブランチをプッシュし、プルリクエストをする。
注意！:セパレートブランチを作って行わないと、
A さんが B さんの変更方針をレビューした上でアップデートすることができない。
プルリクエスト経由でマージできない。
ダイアグラムを書いて理解するのが早道かもしれん。
-------------------------------------------------
▼pullしてmodify中に、オリジナルがマージされ更新された場合//Fork が、ではなくオリジナルが
-------------------------------------------------
- 状況
Original を Fork したものを Local に Pull して変更し、Fork に Push した際に、
すでに Original が修正されていて、再同期が必要な時。
-------------------------------------------------
Origin としてのリモートは Fork のレポジトリになっている。
そのため、Original のレポジトリのリモートとつながる必要がある。
こういう場合の Original のリモートを、慣例で upstream と呼んでいる。
-------------------------------------------------
upstream をpullすると、upstream/master が出来上がる。
次に、git pull upstream/master を行う。//マスターブランチが最新コミットと同期される。
そして、マスターブランチをセパレートブランチにマージする。//ローカルにて
で、フォークに対し、マスターブランチ及びセパレートブランチを 両方共 プッシュする。
//マスターブランチのプッシュは必須ではないが、そのほうがよいのではないか？とのこと（Udacity 動画にて）
-------------------------------------------------
git remote add upstream http://github.com/udacity/create-your-own-adventure.git
git checkout master
git pull upstream master
git checkout separetedbranch
git merge master separetedbranch
修正 →add →commit
git push origin separetedbranch
git checkout master
git push origin master
-------------------------------------------------
▼Forking
-------------------------------------------------
他のひとの レポジトリを、GitHub サーバに直接コピーできる（ローカルマシーン経由不要）
（GitHub から GitHub への一種のクローニング）
その後、そのレポジトリをローカルに落としてmodifyしていく。
Fork ボタンをクリックすると、フォークされる。
-------------------------------------------------
▼エクササイズ
-------------------------------------------------
クローンしたレポジトリのいち部を修正した上で、他のブランチとマージする
1.クローンして
git clone https://github.com/udacity/asteroids.git
2.▼コミット時の流れ を実施
//modify するポイントは以下
subl game.js の 424 行目に、下記一行を追記し保存
this.delayBeforeBullet = 10;
3.リモートのコミットを確認
git checkout coins
4.マスターブランチを残すように、master と coins とをマージ
5.マージ結果を確認
-------------------------------------------------
■■Git の Hub とのあれこれ
▼ステージングエリア
-------------------------------------------------
コミットは、レポジトリの一部。
ステージングエリアは、直近のコミットのコピー。 ※変更を加えるまでの間は、です。
Working directory の状況も、ステージングエリアと同一。 ※変更を加えるまでの間は、です。
-------------------------------------------------
- git add前のファイルをgit diff
Working directory のファイルに変更を加え、
git add前に、A.Working directory の ファイル * と B.ステージングエリア のファイル とを diff するには、
git diff //※ 引数なし
その後、変更を加えたファイルを git add すると、そのファイルは B に移るため、
git diff しても A のそのファイルと B のそのファイルとの間に差異はない。
-------------------------------------------------
- コミット後のファイル(HEAD)とステージングエリアのファイルとを比較
git diff --staged
-------------------------------------------------
▼ステージしたが、コミットしていない部分の意味
-------------------------------------------------
git add で、ステージすることで、ワーキングとステージとの差分をレビューできる。
自分が、どこをどうmodify していっているかが把握できる。
- code変更点が小さければ小さいほど、レビューは速くて容易になるし、
欠陥の可能性にも気づきやすい。
-------------------------------------------------
▼ステージングエリアの使いみち？
-------------------------------------------------
- git add -A; git commit したらいいんじゃな～い？
そんな時、index があれば、各変更のセットをステージし、
ペンディング状態のものがなくなるまでコミットするだけでいい。
git gui が便利
git add -p も使える。
新しい git なら git add -e もいい。
※どうやら、modify したファイルのさらに一部分だけを、コミットする機能があるらしい。
それが、modify 分割に役立つとのこと。
-------------------------------------------------
▼ステージングエリアを寄り道整理のために使う
-------------------------------------------------
コミットしたいことだけを書く場所と考える
そいつらをステージに上げて、コミットするだけ。
実際はもうちょっととっちらかっている。
ある一つのことを行い、
その途中でたまたま別のことを行う事がある。
その際ステージングエリアを、最初のワークをステージに上げて、コミットする助けとして使う。
その時二つ目のワークをコミットできる状態だったら、コミットする。
そうじゃなかったら、対応を続ける事ができる。
そのコミットは、今自分がやろうとしていることのみを対象にしているってことを確かにしてくれる。
道中でいろんな他のことをやっているとしても。
-------------------------------------------------
▼Git 及び VM、Vagrant のインストール
-------------------------------------------------
udacity の Linux Command Line Basics の下記チュートリアルに従えばよし。
https://classroom.udacity.com/courses/ud595/lessons/4597278561/concepts/47133485700923
-------------------------------------------------
▼Git の設定
-------------------------------------------------
https://classroom.udacity.com/courses/ud775/lessons/2980038599/concepts/33331589510923
上記、Udacity Lesson Navigating a Commit History> Setting Up Your Workspace　を参照
→git-completion.bash　及び　git-prompt.sh　及び　.bash_profile 　をホームディレクトリに保存。
ls が効かない場合、Download　したやつが違っている可能性あり。
- Git から他のアプリを立ち上げるショートカット作成
※sublimeテキストの場合
// ※ファイルパスにスペースが有る場合、スペース前に \ を記載する必要あり。
// ※¥ は \ だが、/ に置き換える。
// 下記を Git で走らせ、Git を閉じて開いて subl と打てば、開くようになる。
//
echo 'alias subl="C:/Program\ Files/Sublime\ Text\ 3/sublime_text.exe"' >> ~/.bash_profile
//下記コマンドでは、スペース前の \ は不要。
git config --global core.editor "'C:/Program Files/Sublime Text 3/sublime_text.exe' -n -w"
//上記エディタが git commit 時に開く
git config --global push.default upstream
git config --global merge.conflictstyle diff3
git config --global gui.encoding utf-8
git config --global core.quotepath false
-------------------------------------------------
