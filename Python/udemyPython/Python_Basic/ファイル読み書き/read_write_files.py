▼ ファイル読み書き時の権限指定
-------------------------------------------------
# read & write を許可
my_file = open('test.txt','w+')
# 開いたファイルは、最後にかならず閉じること！
my_file.close()
-------------------------------------------------
append 権限付与
my_file = open('test.txt','a+')
-------------------------------------------------




▼ ファイルとカーソル
-------------------------------------------------
# サイド読み込むと、読み込み時のカーソルが行末にあるため、何も出力されない。
my_file.read()

# カーソルをファイルの頭(index 0)に移動
my_file.seek(0)

# 読み込むと、出力される
my_file.read()
-------------------------------------------------

▼ with
-------------------------------------------------
・ファイルの読み込み後の close を省略できる
・ファイルの open/close, 通信の start/end, DBアクセスの open/close などで使える
-------------------------------------------------
# with なし
f = open("sample.txt", "r")
print(f.read())
f.close()
-------------------------------------------------
# with あり
with open("sample.txt", "r") as f:
    print(f.read())
-------------------------------------------------
# ネスト
with open("sample1.txt", "r") as f1:
    with open("sample2.txt", "w") as f2:
        f2.write(f1.read())
-------------------------------------------------

▼ 1行ずつ読み込んで、処理していく
-------------------------------------------------
filename = 'pi_digits.txt'
with open(filename) as file_object:
    for line in file_object:
        # print がnewline を行末に追加するので、行間が一行開く。それを防ぐrstrip()
        print(line.rstrip())
-------------------------------------------------



▼ with ブロックを過ぎても、1行ずつ読み込むことができるようにする
-------------------------------------------------
filename = 'pi_digits.txt'

with open(filename) as file_object:
    # ファイル内の行のリストを返す
    # 全行メモリに格納されるため、大きなファイルを使用する際は注意。
    lines = file_object.readlines()

pi_string = ''
for line in lines:
    pi_string += line.rstrip()

print(pi_string)
print(len(pi_string))
-------------------------------------------------




▼ 書き込み
-------------------------------------------------
filename = 'programming.txt'

with open(filename, 'w') as file_object:
    file_object.write("I love programming.")
-------------------------------------------------


▼ append
-------------------------------------------------
filename = 'programming.txt'

with open(filename, 'a') as file_object:
    file_object.write("I also love finding meaning in large datasets.\n")
    file_object.write("I love creating apps that can run in a browser.\n")
-------------------------------------------------
