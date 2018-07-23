from テスト対象モジュール import AnonymousSurvey

# 質問を定義し、サーベイを作成する
question = "初めて学んだ言語はなんですか？"
my_survey = AnonymousSurvey(question)

# 質問を表示して、それに対する回答を格納する
my_survey.show_question()
print("'q'を入力すると、いつでも終了できます。")
while True:
    response = input("言語: ")
    if response == 'q':
        break
    my_survey.store_response(response)

# サーベイの結果を表示する
print("調査に協力いただきありがとうございました！")
my_survey.show_results()
