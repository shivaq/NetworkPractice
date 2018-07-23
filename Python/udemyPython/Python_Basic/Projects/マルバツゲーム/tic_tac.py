
# -------------------------------------------------
# Specifically for the iPython Notebook environment for clearing output
from IPython.display import clear_output
import random

# Global 変数たち
theBoard = [' '] * 10
 # a List Comprehension
available = [str(num) for num in range(0,10)]
# 0 か 1 か ではなく、1 か -1 かにすることで、toggle *= -1 でトグルできるようになる
players = [0,'X','O']   # note that players[1] == 'X' and players[-1] == 'O'

def display_board(a,b):
    print('Available   TIC-TAC-TOE\n'+
           '  moves\n\n  '+
          a[7]+'|'+a[8]+'|'+a[9]+'        '+b[7]+'|'+b[8]+'|'+b[9]+'\n  '+
          '-----        -----\n  '+
          a[4]+'|'+a[5]+'|'+a[6]+'        '+b[4]+'|'+b[5]+'|'+b[6]+'\n  '+
          '-----        -----\n  '+
          a[1]+'|'+a[2]+'|'+a[3]+'        '+b[1]+'|'+b[2]+'|'+b[3]+'\n')
display_board(available,theBoard)
# -------------------------------------------------


# -------------------------------------------------
def chose_position(board,player):
    position = 0

    while position not in [1,2,3,4,5,6,7,8,9] or not is_empty_space(board, position):
        try:
            position = int(input('Player %s, choose your next position: (1-9) '%(player)))
        except:
            print("I'm sorry, please try again.")

    return position
# -------------------------------------------------

# -------------------------------------------------
def is_empty_space(board,position):
    return board[position] == ' '

# からのスペースがないかどうかチェック
def is_every_spaces_marked(board):
    return ' ' not in board[1:]
# -------------------------------------------------

# -------------------------------------------------
def put_marker(avail,board,marker,position):
    board[position] = marker
    avail[position] = ' '
# -------------------------------------------------



# -------------------------------------------------
# 縦、横、斜めで値が揃っているかどうか確認
def win_check(board,mark):

    return ((board[7] == mark and board[8] == mark and board[9] == mark) or # across the top
    (board[4] == mark and board[5] == mark and board[6] == mark) or # across the middle
    (board[1] == mark and board[2] == mark and board[3] == mark) or # across the bottom
    (board[7] == mark and board[4] == mark and board[1] == mark) or # down the middle
    (board[8] == mark and board[5] == mark and board[2] == mark) or # down the middle
    (board[9] == mark and board[6] == mark and board[3] == mark) or # down the right side
    (board[7] == mark and board[5] == mark and board[3] == mark) or # diagonal
    (board[9] == mark and board[5] == mark and board[1] == mark)) # diagonal
# -------------------------------------------------



# -------------------------------------------------
def replay():

    return input('Do you want to play again? Enter Yes or No: ').lower().startswith('y')
# -------------------------------------------------



# -------------------------------------------------
import random

while True:
    clear_output()
    print('Welcome to Tic Tac Toe!')

    # 先攻後攻はランダムで決まる
    toggle = random.choice((-1, 1))
    player = players[toggle]
    print('For this round, Player %s will go first!' %(player))

    game_on = True
    input('Hit Enter to continue')
    while game_on:
        display_board(available,theBoard)
        position = player_choice(theBoard,player)
        put_marker(available,theBoard,player,position)

        if win_check(theBoard, player):
            display_board(available,theBoard)
            print('Congratulations! Player '+player+' wins!')
            game_on = False
        else:
            if is_every_spaces_marked(theBoard):
                display_board(available,theBoard)
                print('The game is a draw!')
                break
            else:
                # プレイヤーのターンがひっくり返る
                toggle *= -1
                player = players[toggle]
                clear_output()

    # reset the board and available moves list
    theBoard = [' '] * 10
    available = [str(num) for num in range(0,10)]

    if not replay():
        break
# -------------------------------------------------
