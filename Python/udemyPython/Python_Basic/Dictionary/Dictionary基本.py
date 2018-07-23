-------------------------------------------------
# 削除
alien_0 = {'color': 'green', 'points': 5}
del alien_0['points']


-------------------------------------------------


# for ループするとキーだけ取り出される
# -------------------------------------------------
d = {'k1':1,'k2':2,'k3':3}

for item in d:
    print(item)
# -------------------------------------------------

# キーから値を呼び出す
-------------------------------------------------
my_dict = {'key1':123,'key2':[12,23,33],'key3':['item0','item1','item2']}

my_dict['key3']
my_dict["key3"][1]
-------------------------------------------------






# キーバリューペアを返す
-------------------------------------------------
user_0 = {
    'username': 'efermi',
    'first': 'enrico',
    'last': 'fermi',
}

for key, value in user_0.items():
    print("\nKey: " + key)
    print("Value: " + value)
-------------------------------------------------

# (key, value) tuple を返す
-------------------------------------------------
d.items()
-------------------------------------------------

# キーを返す
-------------------------------------------------
favorite_languages = {
    languages.py 'jen': 'python',
    'sarah': 'c',
    'edward': 'ruby',
    'phil': 'python',
}

for name in favorite_languages.keys():
    print(name.title())
-------------------------------------------------

# 値を返す
-------------------------------------------------
for language in favorite_languages.values():
    print(language.title())
-------------------------------------------------




# dictionaries are unordered
-------------------------------------------------
sorted(d.values())
-------------------------------------------------

# キーを順序建てて返す
-------------------------------------------------
# ループ前に、事前にキーをリストアップして、ソートされる
for name in sorted(favorite_languages.keys()):
    print(name.title() + ", thank you for taking the poll.")
-------------------------------------------------


# 重複を排除してループ
-------------------------------------------------
for language in set(favorite_languages.values()):
    print(language.title())
-------------------------------------------------





▼ while ループ で辞書づくり
-------------------------------------------------
responses = {}

# ポーリングフラグ
polling_active = True

while polling_active:

    name = input("\nWhat is your name? ")
    response = input("Which mountain would you like to climb someday? ")

    # 辞書に name 変数の値キーの値に response を格納
    responses[name] = response

    # 続けるかい？的なプロンプト
    repeat = input("Would you like to let another person respond? (yes/ no) ")
    if repeat == 'no':
        polling_active = False

# 回答回収完了
print("\n--- Poll Results ---")
for name, response in responses.items():
    print(name + " would like to climb " + response + ".")
-------------------------------------------------






▼ オプションとして辞書の第三のキーを受け入れ
-------------------------------------------------
# age のデフォ値は空欄
def build_person(first_name, last_name, age=''):
    """Return a dictionary of information about a person."""
    person = {'first': first_name, 'last': last_name}

    if age:# age が空でなければ
        person['age'] = age# 辞書にage キーが追加
    return person

musician = build_person('jimi', 'hendrix', age=27)
print(musician)
-------------------------------------------------
