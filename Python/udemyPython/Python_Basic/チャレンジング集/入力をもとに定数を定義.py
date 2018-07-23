# -------------------------------------------------
def player_input():
    marker = ''

    while not (marker == 'X' or marker == 'O'):
        # 入力を要求し
        marker = input('Player 1: Do you want to be X or O? ').upper()

    # 入力によってtuple が返される
    if marker == 'X':
        return ('X', 'O')
    else:
        return ('O', 'X')



# 右辺から返る tuple の要素が、2つの変数に、それぞれ入力される
    player1_marker, player2_marker = player_input()
# -------------------------------------------------
