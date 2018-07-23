
▼ インプットを要求
-------------------------------------------------
        marker = input('Player 1: Do you want to be X or O? ').upper()
-------------------------------------------------

▼ 入力値のサニティーチェック + プロンプト文字列
-------------------------------------------------
    while position not in [1,2,3,4,5,6,7,8,9] or not is_empty_space(board, position):
        position = int(input('Choose your next position: (1-9) '))
-------------------------------------------------


▼ プロンプト文字列を格納しておいて、クリアなコードに
-------------------------------------------------
prompt = "If you tell us who you are, we can personalize the messages you see."
prompt += "\nWhat is your first name? "

name = input(prompt)
print("\nHello, " + name + "!")
-------------------------------------------------



▼ Input では数値が文字列になってしまうので、int()
-------------------------------------------------
height = input("How tall are you, in inches? ")
height = int(height)

if height >= 36:
        print("\nYou're tall enough to ride!")
else:
        print("\nYou'll be able to ride when you're a little older.")
-------------------------------------------------










