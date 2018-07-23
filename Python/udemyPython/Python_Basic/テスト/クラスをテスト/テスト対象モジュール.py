class AnonymousSurvey():
    """サーベイの質問に対する、匿名の回答を収集する"""

    def __init__(self, question):
        """質問を格納 + 回答を格納する準備をする"""
        self.question = question
        self.responses = []

    def show_question(self):
        """質問を表示する"""
        print(self.question)

    def store_response(self, new_response):
        """サーベイに対する回答を格納する"""
        self.responses.append(new_response)

    def show_results(self):
        """受け取ったすべての回答を表示する"""
        print("回答結果:")
        for response in self.responses:
            print('- ' + response)
