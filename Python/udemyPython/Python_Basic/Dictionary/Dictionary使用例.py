▼ Dictionary をリストに格納して、キャラクターDictionary を作成
-------------------------------------------------
# エイリアンを格納するからのリストを作成
aliens = []

# Make 30 green aliens.
for alien_number in range(30):
    new_alien = {'color': 'green', 'points': 5, 'speed': 'slow'}
    aliens.append(new_alien)

# エイリアンリストのうち、最初の3つの、緑のやつのパラメータを変更
for alien in aliens[0:3]:
    if alien['color'] == 'green':
        alien['color'] = 'yellow'
        alien['speed'] = 'medium'
        alien['points'] = 10
    elif alien['color'] == 'yellow':
        alien['color'] = 'red'
        alien['speed'] = 'fast'
        alien['points'] = 15

# Show the first 5 aliens:
for alien in aliens[:5]:
    print(alien)
    print("...")

# Show how many aliens have been created.
print("Total number of aliens: " + str(len(aliens)))
-------------------------------------------------



▼ Dictionary 内に List を格納して、ピザオーダーリスト作成
-------------------------------------------------
# Store information about a pizza being ordered.
 pizza = {
    'crust': 'thick',
    'toppings': ['mushrooms', 'extra cheese'],
}

# Summarize the order.
print("You ordered a " + pizza['crust'] + "-crust pizza " +
    "with the following toppings:")
for topping in pizza['toppings']:
    print("\t" + topping)

favorite_languages = {
    'jen': ['python', 'ruby'],
    'sarah': ['c'],
    'edward': ['ruby', 'go'],
    'phil': ['python', 'haskell'],
}

-------------------------------------------------

▼ 辞書内辞書で、ユーザーの属性を記述
-------------------------------------------------
users = {
'aeinstein': {
    'first': 'albert',
    'last': 'einstein',
    'location': 'princeton',
},
'mcurie': {
    'first': 'marie',
    'last': 'curie',
    'location': 'paris',
},
}
for username, user_info in users.items():
    print("\nUsername: " + username)
    full_name = user_info['first'] + " " + user_info['last']
    location = user_info['location']
print("\tFull name: " + full_name.title())
-------------------------------------------------

