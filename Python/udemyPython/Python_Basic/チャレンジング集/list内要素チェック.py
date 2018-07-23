▼ 入力させた値が、while の条件に合致する限りループし続ける(有効範囲内の値ではない、殻のスペース ではない)
-------------------------------------------------
    position = 0
    available_range =  [1,2,3,4,5,6,7,8,9]

    while position not in available_range or not is_empty_space(board, position):
        try:
            position = int(input('Player %s, choose your next position: (1-9) '%(player)))
        except:
            print("I'm sorry, please try again.")
-------------------------------------------------



▼ リスト内に、該当する値がないかどうかチェック
-------------------------------------------------
# からのスペースがないかどうかチェック
def is_every_spaces_marked(board):
    return ' ' not in board[1:]
-------------------------------------------------
