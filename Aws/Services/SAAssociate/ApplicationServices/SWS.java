▼ Simple Workflow Service
-------------------------------------------------
AWS だけでなく、人間のタスクも組み込んだ、全体のワークフローを管理
・Task を使って、アプリのコンポーネントを連携させる
// Amazon でものを買う～倉庫でぶつをピック →バーコードを読み取ってパッケージング
//  →などなどの一連の処理にも使用
・Task →複数の処理の連携(ユースケース)を開始させる
// メディアプロセス、ウェブアプリのバックエンド、ビジネスプロセスワークフロー、
// アナリティクスパイプライン
・Task を起動させるもの
// EXE コード、ウェブサービスコール、Human アクション、スクリプト
・Retention ピリオド最大 1 年
// SQS は 14日
・タスク Oriented API
// ワークフロー全体の管理が特徴
// SQS は メッセージ指向API。デカップリングが特徴
・タスク割当が一度限りであることを保証// 重複なし
・アプリ内のすべての Task 及び イベントを追跡
// SQS はアプリレベルの追跡システムを、自前で実装する必要あり。マルチキューの場合は特に

-------------------------------------------------


▼ SWF アクターズ
-------------------------------------------------
・Workflow スターターズ
// ワークフローを開始するアプリ
// オーダーボタンクリック、バスの時間を検索
・Deciders
// Task のフローをコントロール
// ワークフロー内で何かが終了/失敗 →次のアクションを判断
・Activity ワーカーズ
// Activity Tasks を実行

例
Amazon.com のフロントエンドで
オーダー →クレジットカード使えない
 →(Decider) クレジットカードが使えないので別の支払い方法を紹介
  →他の支払い承認 →(Activity worker)倉庫の人に、商品を取りに行かせる
-------------------------------------------------