▼ Setting
-------------------------------------------------
Appearance & Behavior］ > ［Appearance］
［Window Options］の「Show memory indicator」にチェック

［Show line numbers］および［Show whitespaces］にチェック

［Editor］ > ［General］ > ［Code Completion］ →None
［Editor］ > ［General］ > ［Editor Tabs］ →50
［Preferences］ > ［Editor］ > ［Color & Fonts］ > ［Font］ →10
［Preferences］ > ［Editor］ > ［Color & Fonts］ > ［Console Font］ →10
Editor＞Inspections>Python>PEP 8 coding ..., PEP 8 nameing ... を Warning に変更

プラグイン # Plugin > Brouse Repository
AceJump
CodeGlance（Sublime Text のミニマップ風）
gfm（GitHub Markdown プラグイン）
.ignore
-------------------------------------------------

インストールしたモジュールが pycharm に認識されなかった
・pip show pygame では、認識された
 → settings>project>project interpreter
 ここで、入れたいモジュールを検索してインストールして認識された
-------------------------------------------------

▼ メモリ関係
-------------------------------------------------
- メモリをクリア
    右下の、メモリインジケータをクリック


- デフォルトのヒープメモリサイズを 2GB に増やす
［Help］ > ［Edit Custom VM Options］
-------------------------------------------------
# custom PyCharm VM options

-Xms128m
-Xmx2000m # ヒープメモリサイズを 2GB に増やす
-XX:ReservedCodeCacheSize=240m
-XX:+UseCompressedOops
-------------------------------------------------
 →PyCharm を再起動
-------------------------------------------------

タイプヒント
-------------------------------------------------
Alt + Enter

-------------------------------------------------


検索
-------------------------------------------------
shift ダブルクリック

ファイル移動
Ctrl + e

grep
Shift Ctrl F

Ctrl + bug

Alt Ctrl F7

Alt Ctrl 左右(履歴の前後)  上下(Grepの前後)
-------------------------------------------------
