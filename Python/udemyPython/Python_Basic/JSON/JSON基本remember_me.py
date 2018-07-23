import json

def get_stored_username():
    """Get stored username if available."""
    filename = 'username.json'

    try:
        with open(filename) as f_obj:
            # jsonファイルが有った場合、▼ JSONファイルを読み込み
            username = json.load(f_obj)
    except FileNotFoundError:
        return None
    else:
        return username


def get_new_username():
    """Prompt for a new username."""
    # ユーザーに入力を求める
    username = input("What is your name? ")
    filename = 'username.json'
    with open(filename, 'w') as f_obj:
        # ▼ JSONファイルを作成、書き込み
        json.dump(username, f_obj)
    return username


def greet_user():
    """Greet the user by name."""
    username = get_stored_username()
    if username:
        print("Welcome back, " + username + "!")
    # JSONファイルがなかった場合
    else:
        username = get_new_username()
        print("We'll remember you when you come back, " + username + "!")

greet_user()
