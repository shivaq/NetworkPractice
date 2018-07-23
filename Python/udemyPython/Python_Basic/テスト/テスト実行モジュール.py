import unittest
from テスト対象モジュール import get_formatted_name

"""このモジュールを走らせれば、対象関数を繰り返しテストできる"""
# 1.unittest をインポート
# 2.テスト対象の関数をインポート

# unittest.TestCase を継承
class NamesTest(unittest.TestCase):
    """'テスト対象モジュール.py' のテスト
    クラスは対象モジュールを表す名前に。
    各テスト関数は、テストケースを表す名前に。
    """

    def test_first_last_name(self):
        """ 'Janis Joplin' みたいな名前はワークするか"""
        formatted_name = get_formatted_name("janis", "joplin")
        self.assertEqual(formatted_name, 'Janis Joplin')

        # テストの関数名は test_ から始まってないと、対象にならない
    def test_first_last_middle_name(self):
        """’Wolfgang Amadeus Mozart' みたいな名前はワークするか"""
        formatted_name = get_formatted_name(
            'wolfgang', 'mozart', 'amadeus')
        self.assertEqual(formatted_name, "Wolfgang Amadeus Mozart")

unittest.main()
