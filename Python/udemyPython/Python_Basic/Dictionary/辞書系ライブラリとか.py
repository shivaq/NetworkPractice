
▼ 順序が保たれている辞書# OrderedDict
-------------------------------------------------
from collections import OrderedDict

favorite_languages = OrderedDict()
# {}ではなく[]
favorite_languages['jen'] = 'python'
favorite_languages['sarah'] = 'c'
favorite_languages['edward'] = 'ruby'
favorite_languages['phil'] = 'python'

for name, language in favorite_languages.items():
    print(name.title() + "'s favorite language is " +
    language.title() + ".")
-------------------------------------------------
