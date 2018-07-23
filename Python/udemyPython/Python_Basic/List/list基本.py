-------------------------------------------------
# 追加
motorcycles = ['honda', 'yamaha', 'suzuki']
motorcycles.append('ducati')

# 挿入
motorcycles.insert(0, 'ducati')
print(motorcycles)

# 削除
del motorcycles[0]
motorcycles.remove('ducati')

# pop
popped_motorcycle = motorcycles.pop()
print(popped_motorcycle)
suzuki
first_owned = motorcycles.pop(0)

# ソート
cars = ['bmw', 'audi', 'toyota', 'subaru']
cars.sort()
cars.sort(reverse=True)
# 一時的にソートするけどもとの順序は維持
print(sorted(cars))

# 逆順にする
cars.reverse()

# 長さ
len(cars)

# List の最後の要素（インデックスエラーを回避）
motorcycles[-1]
-------------------------------------------------

# List 連結
# -------------------------------------------------
spam = [1, 2, 3]
spam = spam + [a, b, c]
# -------------------------------------------------

# List から要素削除
# -------------------------------------------------
spam = ["cat", "bat", "rat", "elephant"]
del spam[2]
# -------------------------------------------------


# List と Pop
# -------------------------------------------------
# Create a new list
list1 = [1,2,3]

# Append
list1.append('append me!')

# Pop off the 0 indexed item
list1.pop(0)

# ポップした要素を割り当て。デフォルトインデックスは -1
popped_item = list1.pop()
-------------------------------------------------


# List 内要素チェック
-------------------------------------------------
# 含んでいるか
requested_toppings = ['mushrooms', 'onions', 'pineapple']
    'mushrooms' in requested_toppings
-------------------------------------------------
# 含まれていないか？     not を使う
banned_users = ['andrew', 'carolina', 'david']
user = 'marie'

if user not in banned_users:
    print(user.title() + ", you can post a response if you wish.")
-------------------------------------------------
# List が空かどうかチェック

requested_toppings = []
if requested_toppings:
    for requested_topping in requested_toppings:
        print("Adding " + requested_topping + ".")
        print("\nFinished making your pizza!")
            else:
        print("Are you sure you want a plain pizza?")
-------------------------------------------------






# List ネストしてマトリックス作成
# -------------------------------------------------
# Let's make three lists
lst_1=[1,2,3]
lst_2=[4,5,6]
lst_3=[7,8,9]

# Show
matrix

# Make a list of lists to form a matrix
matrix = [lst_1,lst_2,lst_3]

# Grab first item in matrix object
matrix[0]

# Grab first item of the first item in the matrix object
matrix[0][0]
# -------------------------------------------------









# List Comprehensions
-------------------------------------------------
squares = []
for value in range(1,11):
  squares.append(value**2)
-------------------------------------------------
squares = [value**2 for value in range(1, 11)]
-------------------------------------------------
# Let's make three lists
lst_1=[1,2,3]
lst_2=[4,5,6]
lst_3=[7,8,9]

# Make a list of lists to form a matrix
matrix = [lst_1,lst_2,lst_3]

first_col = [row[0] for row in matrix]

# Check for even numbers in a range
lst = [x for x in range(11) if x % 2 == 0]

# Convert Celsius to Fahrenheit
celsius = [0,10,20.1,34.5]

fahrenheit = [((9/5)*temp + 32) for temp in celsius ]

fahrenheit
# -------------------------------------------------

# リストのスライス作成
-------------------------------------------------
players = ['charles', 'martina', 'michael', 'florence', 'eli']
print(players[0:3])
# インデックス 0 から 2 までの計 3つの要素を出力
print(players[1:4])
# インデックス 1 から 3 までの計 3つの要素を出力
print(players[:4])
# インデックス 0 から 3 までの計 4つの要素を出力

# for ループに使用
for player in players[:3]:
print(player.title())
-------------------------------------------------




▼ List のコピー
-------------------------------------------------
my_foods = ['pizza', 'falafel', 'carrot cake']

# こうすると、参照がこぴーされるだけ
friend_foods = my_foods
# もとのListの最初から最後までの要素をコピー
friend_foods = my_foods[:]
-------------------------------------------------




















▼ List 要素の処理は、while ループを使いましょう
# for ループは、List の要素の 追跡 ができない
while ループで、List A から要素を取り出し、処理をし、List B に振り分ける
-------------------------------------------------
# verify 対象のユーザー
unconfirmed_users = ['alice', 'brian', 'candace']
# verify 済みユーザーを格納するリスト
confirmed_users = []

# unconfirmed_users が空になるまで処理を続ける
while unconfirmed_users:
    # List A からポップして
    current_user = unconfirmed_users.pop()

    print("Verifying user: " + current_user.title())
    # List B に格納していく
    confirmed_users.append(current_user)

# Display all confirmed users.
print("\nThe following users have been confirmed:")
for confirmed_user in confirmed_users:
    print(confirmed_user.title())
-------------------------------------------------



▼ List から特定の値の要素を削除ループ
-------------------------------------------------
pets = ['dog', 'cat', 'dog', 'goldfish', 'cat', 'rabbit', 'cat']
print(pets)

while 'cat' in pets:
    pets.remove('cat')

print(pets)
-------------------------------------------------




▼ 処理前リストから、処理完了リストに仕分け関数
# 関数にリストを渡し、リストが変更される
-------------------------------------------------
def print_models(unprinted_designs, completed_models):
    """
    リストから各要素がなくなるまで出力。
    出力された要素は、完了リストに格納。
    """
    while unprinted_designs:
        current_design = unprinted_designs.pop()
        # Simulate creating a 3D print from the design.
        print("Printing model: " + current_design)
        completed_models.append(current_design)

def show_completed_models(completed_models):
    """出力された全モデルを表示."""
    print("\nThe following models have been printed:")

    for completed_model in completed_models:
        print(completed_model)

completed_models = []
unprinted_designs = ['iphone case', 'robot pendant', 'dodecahedron']

print_models(unprinted_designs, completed_models)
show_completed_models(completed_models)
-------------------------------------------------



▼ 関数にList のコピーを渡す
# List は変更されない
-------------------------------------------------
# コピーが渡される
function_name(list_name[:])

-------------------------------------------------
