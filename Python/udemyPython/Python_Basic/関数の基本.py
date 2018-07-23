▼ 長い引数や関数名のときの推奨改行フォーマット
-------------------------------------------------
def function_name(
        parameter_0, parameter_1, parameter_2,
        parameter_3, parameter_4, parameter_5):
    function body...
-------------------------------------------------

▼ デフォルト値
-------------------------------------------------
def describe_pet(pet_name, animal_type='dog'):
-------------------------------------------------

▼ 返り値
-------------------------------------------------
def get_formatted_name(first_name, last_name):
    """Return a full name, neatly formatted."""
    full_name = first_name + ' ' + last_name
    return full_name.title()

musician = get_formatted_name('jimi', 'hendrix')
print(musician)
-------------------------------------------------


▼ Argument を オプション扱いにする
# デフォ値を空欄に設定
-------------------------------------------------
def get_formatted_name(first_name, last_name, middle_name=''):
    """Return a full name, neatly formatted."""
    if middle_name:
        full_name = first_name + ' ' + middle_name + ' ' + last_name
    else:
        full_name = first_name + ' ' + last_name
    return full_name.title()

musician = get_formatted_name('jimi', 'hendrix')
print(musician)

musician = get_formatted_name('john', 'hooker', 'lee')
print(musician)
-------------------------------------------------


▼ 関数の引数で任意の数を受け付ける
-------------------------------------------------
def make_pizza(*toppings):
    """Print the list of toppings that have been requested."""
    print(toppings)

    for topping in toppings:
        print("- " + topping)

make_pizza('pepperoni')
make_pizza('mushrooms', 'green peppers', 'extra cheese')
-------------------------------------------------
def make_pizza(size, *toppings):
    """Summarize the pizza we are about to make."""
    print("\nMaking a " + str(size) +
        "-inch pizza with the following toppings:")
    for topping in toppings:
        print("- " + topping)

make_pizza(16, 'pepperoni')
make_pizza(12, 'mushrooms', 'green peppers', 'extra cheese')
-------------------------------------------------






▼ 関数の引数で、「任意の」引数を表す # 数が不明の場合
-------------------------------------------------
- 渡された引数は tuple として扱われる
- * から始まる文字列なら、argsでなくてもいい
-------------------------------------------------
 *args を使わない場合
# とりあえず、複数の引数と、デフォ値を定義しておく
def myfunc(a=0,b=0,c=0,d=0,e=0):
    return sum((a,b,c,d,e))*.05

myfunc(40,60,20)
-------------------------------------------------
*args を使う場合

def myfunc(*args):
    return sum(args)*.05

myfunc(40,60,20)
-------------------------------------------------




▼ 関数の引数で、任意の 「Dictionary」 を受ける #情報の種類が不明の場合
-------------------------------------------------
def myfunc(*args, **kwargs):
    if 'fruit' and 'juice' in kwargs:
        print(f"I like {' and '.join(args)} and my favorite fruit is {kwargs['fruit']}")
        print(f"May I have some {kwargs['juice']} juice?")
    else:
        pass

myfunc('eggs','spam',fruit='cherries',juice='orange')
-------------------------------------------------
# 普通の引数の前に、kwargs 等を渡すとエラー
myfunc(fruit='cherries',juice='orange','eggs','spam')
-------------------------------------------------
def build_profile(first, last, **user_info):
    """Build a dictionary containing everything we know about a user."""
    profile = {}
    profile['first_name'] = first
    profile['last_name'] = last
    for key, value in user_info.items():
        profile[key] = value
    return profile

user_profile = build_profile('albert', 'einstein',
                            location='princeton',
                            field='physics')

print(user_profile)
-------------------------------------------------
# 出力結果
{'first_name': 'albert', 'last_name': 'einstein',
'location': 'princeton', 'field': 'physics'}
-------------------------------------------------
