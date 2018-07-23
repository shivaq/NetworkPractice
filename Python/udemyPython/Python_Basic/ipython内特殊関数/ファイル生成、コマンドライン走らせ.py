▼ ファイル生成 # %%writefile create_even_num.py
-------------------------------------------------
%%writefile create_even_num.py

def createEvenNum(range_for_even_nums):
    even_list = [x for x in range(range_for_even_nums) if x % 2 == 0]
    return even_list
-------------------------------------------------
%%writefile giveSeedForEvenNumByArg.py
import sys
# sys モジュールを使用 ⇒スクリプトをコールする際の引数にアクセスできる。
import create_even_num

# [0] は本ファイル "giveSeedForEvenNumByArg", [1] が第一引数を指す
num = int(sys.argv[1])
print(create_even_num.createEvenNum(num))
-------------------------------------------------



▼ コマンドライン走らせ # ! python
-------------------------------------------------
! python giveSeedForEvenNumByArg.py 100
-------------------------------------------------
