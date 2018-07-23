    def __init__(self,var1,var2):
-------------------------------------------------
クラスから新規インスタンスを作成する際に、自動的に呼び出される
-------------------------------------------------

self
-------------------------------------------------
インスタンス自身を指す引数であり、個々のインスタンスのメソッドや属性へのアクセス、になる。
あらゆるクラスのメソッド呼び出し時には、Pythonによって self が渡される。
定義時には含めるが、呼び出し時には記載不要
最初の引数にする必要あり。
        # 下記で、引数の値が、インスタンスの変数として定義される。
        self.breed = breed
-------------------------------------------------
8




▼ クラスをインポート
# car.py から Car, ElectricCar クラスをインポート
-------------------------------------------------
# my_car.py
from car import Car, ElectricCar

my_new_car = Car("audi", "a4", 2016)
my_new_car.odometer_reading = 23
-------------------------------------------------
# クラス全体をインポート
import car
-------------------------------------------------







▼ クラス定義
-------------------------------------------------
class Dog():
    """A simple attempt to model a dog."""
    # クラス OBJ 属性。全インスタンス共通になる。慣例として、_init_ 前に宣言
    species = 'mammal'

    # OBJ の属性を初期化
    # OBJ 生成時にコールされる
    def __init__(self,breed,name):
        # 属性作成シンタックス
        # 下記で、引数の値が、インスタンスの変数として定義される。
        self.breed = breed
        self.name = name
        # コンストラクタの引数には含めず、デフォ値を設定。
        self.odometer_reading = 0

    def sit(self):
        # クラス内の関数は、メソッドと呼ばれる
        """Simulate a dog sitting in response to a command."""
        print(self.name.title() + " is now sitting.")
-------------------------------------------------


▼ クラス変数にアクセス
-------------------------------------------------
    def sit(self):
        # self. をプリフィックスにつけないと、アクセスできない
        print(self.name.title() + " is now sitting.")

-------------------------------------------------


▼ インスタンスの作成方法
-------------------------------------------------
sam = Dog('Lab','Sam')
frank = Dog(breed='Huskie')
-------------------------------------------------
▼ インスタンスに直接アクセス
-------------------------------------------------
sam.species = "Alien"
sam.breed
sam.name
frank.breed
-------------------------------------------------



▼ セッターゲッター
-------------------------------------------------
class Circle():
    # クラス OBJ 属性  (static 変数みたいなもの？)
    pi = 3.14

    # 半径とともにインスタンス化。半径の デフォルトは 1
    def __init__(self, radius=1):
        # インスタンス化されるインスタンスの radius 属性  (ローカルではなく、メンバー変数的なスコープ)
        self.radius = radius

        # 自身の pi 属性がまだないため、クラスOBJ属性 Circle.piをコール
        self.area = radius * radius * Circle.pi

    # セッター
    def setRadius(self, new_radius):
        self.radius = new_radius
        # 既存の OBJ に働きかけるので、self.pi をコール
        self.area = new_radius * new_radius * self.pi

    # ゲッター
    def getCircumference(self):
        return self.radius * self.pi * 2


c = Circle()

print('Radius is: ',c.radius)# 属性にアクセス
print('Area is: ',c.area)
print('Circumference is: ',c.getCircumference()) # ゲッターメソッドにアクセス

c.setRadius(2)

print('Radius is: ',c.radius)
print('Area is: ',c.area)
print('Circumference is: ',c.getCircumference())
-------------------------------------------------







継承
-------------------------------------------------
class Animal:
    def __init__(self):
        print("Animal created")

    def whoAmI(self):
        print("Animal")

    def eat(self):
        print("Eating")

# 継承するには、クラスの引数に親クラス名を渡すみたいな感じ？
class Dog(Animal):
    def __init__(self):
        # 継承するには、親クラスを子クラスinit 内で init する？
        super().__init__(self)
        # 子クラスで、新規変数を定義
        self.battery_size = 70


        print("Dog created")

    def whoAmI(self):
        print("Dog")

    def bark(self):
        print("Woof!")

    # オーバーライド
    def eat():
        print("A Dog doesn't eat anything!")

d = Dog()
d.whoAmI()
d.eat()
d.bark()
-------------------------------------------------



▼ コンポーネントもクラス化して、別クラスに取り込む
-------------------------------------------------
class Car():
    --snip--

class Battery():# バッテリークラスを作成
    """A simple attempt to model a battery for an electric car."""

    def __init__(self, battery_size=70):# もちろん __init__
        """Initialize the battery's attributes."""
        self.battery_size = battery_size

    def describe_battery(self):
        """Print a statement describing the battery size."""
        print("This car has a " + str(self.battery_size) + "-kWh battery.")

class ElectricCar(Car):
    """Represent aspects of a car, specific to electric vehicles."""

    def __init__(self, make, model, year):
        """
        Initialize attributes of the parent class.
        Then initialize attributes specific to an electric car.
        """
        super().__init__(make, model, year)
        self.battery = Battery()# バッテリークラスのインスタンスを、battery 属性に格納

my_tesla = ElectricCar('tesla', 'model s', 2016)
print(my_tesla.get_descriptive_name())
my_tesla.battery.describe_battery()# バッテリー属性はインスタンスなので、メソッドを呼び出せる
-------------------------------------------------













▼ ポリフォーミズム
-------------------------------------------------
異なるファイルタイプを開く - ワード、エクセル、PDF など、異なるツールが必要
異なる OBJ を追加 - + オペレータは、足し算を行うことも、連結を行うこともある
-------------------------------------------------
# 抽象クラス的位置づけ
class Animal:
    def __init__(self, name):    # Constructor of the class
        self.name = name

    # 抽象メソッド
    def speak(self):              # Abstract method, defined by convention only
        raise NotImplementedError("Subclass must implement abstract method")

# 抽象クラスを継承しているかたちになる
class Dog(Animal):

    def speak(self):
        return self.name+' says Woof!'

class Cat(Animal):

    def speak(self):
        return self.name+' says Meow!'

fido = Dog('Fido')
isis = Cat('Isis')
-------------------------------------------------
for pet in [fido,isis]:
    print(pet.speak())

def pet_speak(pet):
    print(pet.speak())

pet_speak(niko)
pet_speak(felix)
-------------------------------------------------



▼ toString()的なもの、len()で返す値算出法を定義, GC時にコールされる機能の特別なメソッド
-------------------------------------------------
class Book:
    def __init__(self, title, author, pages):
        print("A book is created")
        self.title = title
        self.author = author
        self.pages = pages

    # OBJ の 情報を出力する java における toString() みたいなもの。
    def __str__(self):
        return "Title: %s, author: %s, pages: %s" %(self.title, self.author, self.pages)

    # len() と同じ機能をもたせるもの
    def __len__(self):
        return self.pages

    # GC 時にコールされる。
    def __del__(self):
        print("A book is destroyed")
-------------------------------------------------
book = Book("Python Rocks!", "Jose Portilla", 159)

#Special Methods
print(book)
print(len(book))
del book
-------------------------------------------------
