import random

secretNumber = random.randint(1, 20)
print("1 から 20 の間の数値から、一つの番号がランダムで選ばれます。その数値をあててください")

# Ask the player to guess 6 times.
for guesses_taken in range(1, 7):
    print("1～20の間の数値を入力して当ててください")
    guess = int(input())

    if guess < secretNumber:
        print("小さすぎ")
    elif guess > secretNumber:
        print("大きすぎ")
    else:
        break

if guess == secretNumber:
    print("あたり！ 正解は " +str(secretNumber) + "です"+ str(guesses_taken) + "回で当たりました")
else:
    print("はずれ！正解は " + str(secretNumber))
