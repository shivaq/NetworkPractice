import unittest
from annonymous_survey import AnonymousSurvey


class TestAnonymousSurvey(unittest.TestCase):
    """AnonymousSurvey クラスのテスト"""

    def setUp(self):
        """テストメソッド用に、サーベイと回答群を生成する"""

        question = "初めて学んだ言語はなんですか？"
        self.my_survey = AnonymousSurvey(question)
        self.responses = ['English', 'Spanish', 'Mandarin']

    def test_store_single_response(self):
        """1つの回答が正しく格納されていることかどうかテスト"""

        self.my_survey.store_response(self.responses[0])
        self.assertIn(self.responses[0], self.my_survey.responses)

    def test_store_three_responses(self):
        """3つの回答が適切に格納されているかテスト"""

        for response in self.responses:
            self.my_survey.store_response(response)
        for response in self.responses:
            self.assertIn(response, self.my_survey.responses)


unittest.main()
