import json

numbers = [2,3,6,88,4]

filename = 'numbers.json'
with open(filename, "w") as f_obj:
    # 引数1 の値を 引数2 の名前のファイルに格納
    json.dump(numbers, f_obj)
