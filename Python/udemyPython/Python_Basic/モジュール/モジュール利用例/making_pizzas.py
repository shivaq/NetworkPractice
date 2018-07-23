# 全体をインポート
import pizza

# 全モジュールインポート →ドットを使ってモジュールと関数指定しなくていい。
# 直接関数を呼び出せる。しかし、同名関数があったら問題発生なので非推奨
# from module_name import *

# モジュール内の、関数指定でインポート
# from module_name import function_0, function_1, function_2

# インポートして、関数のエイリアスを定義
# from pizza import make_pizza as mp

# インポートして、モジュールレベルでエイリアスを定義
# import pizza as p

pizza.make_pizza(16, "pepperoni")
pizza.make_pizza(12, "mushrooms", "green peppers", "extra cheese")

# mp(16, 'pepperoni')

# p.make_pizza(16, 'pepperoni')
