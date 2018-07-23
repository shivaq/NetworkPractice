if
-------------------------------------------------
loc = 'Bank'

if loc == 'Auto Shop':
    print('Welcome to the Auto Shop!')
elif loc == 'Bank':
    print('Welcome to the bank!')
else:
    print('Where are you?')
-------------------------------------------------
age = 12

if age < 4:
    price = 0
elif age < 18:
    price = 5
elif age < 65:
    price = 10
# else ではなく、elif を使ったほうが、意図がクリア
elif age >= 65:
    price = 5
-------------------------------------------------
# AND
age_0 >= 21 and age_1 >= 21
# OR
age_0 >= 21 or age_1 >= 21
# for a better readability
(age_0 >= 21) and (age_1 >= 21)
-------------------------------------------------







#  文字列は、空欄をFalse と評価される
# -------------------------------------------------
name = ""
while not name:
# -------------------------------------------------


# 最大値は 5 で、最後から、ひとつずつ減っていく
# -------------------------------------------------
for i in range(5, -1, -1)
# -------------------------------------------------







▼ For ループ
-------------------------------------------------
list1 = [1,2,3,4,5,6,7,8,9,10]
for num in list1:
    if num % 2 == 0:
        print(num)
    else:
        print('Odd number')
-------------------------------------------------
tup = (1,2,3,4,5)

for t in tup:
    print(t)
-------------------------------------------------


▼ for ループ後に文字列出力
-------------------------------------------------
magicians = ['alice', 'david', 'carolina']
for magician in magicians:
  print(magician.title() + ", that was a great trick!")
  print("I can't wait to see your next trick, " + magician.title() + ".\n")

print("Thank you, everyone. That was a great magic show!")
-------------------------------------------------








■■ While ■■
# While True で、break するまで回り続ける
# -------------------------------------------------
while True:
    print("Who are you?")
    name = input()
    if name != "Ted"
        continue
    print("Hello")
# -------------------------------------------------



▼ フラグを使ってループ管理
# ユーザーが quit を入力したらループを抜ける
-------------------------------------------------
prompt = "\nTell me something, and I will repeat it back to you:"
prompt += "\nEnter 'quit' to end the program. "

active = True
while active:
    message = input(prompt)

    if message == 'quit':
        active = False
    else:
        print(message)
-------------------------------------------------




▼ break するまで、ループするのをやめない！
-------------------------------------------------
prompt = "\n行ったことのある町の名前を記入してください:"
prompt += "\n(終わったら 'quit' を入力してください) "

while True:
    city = input(prompt)
if city == 'quit':
    break
else:
    print("I'd love to go to " + city.title() + "!")
-------------------------------------------------




▼ continue で、入り口に強制的に戻らされる
-------------------------------------------------
current_number = 0

while current_number < 10:
    current_number += 1
    if current_number % 2 == 0:
        continue
    print(current_number)
-------------------------------------------------
