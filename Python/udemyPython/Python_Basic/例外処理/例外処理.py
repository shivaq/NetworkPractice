▼ 例外処理基本
-------------------------------------------------
try: # 例外処理が起こり得るコードを try ブロックに配置
    f = open('testfile','w')
    f.write('Test write this')
except IOError: # 例外処理は except ブロックに配置
    # IOError のみチェック
    print("Error: Could not find file or read data")
else:
    print("Content written successfully")
    f.close()
-------------------------------------------------



▼ finally
-------------------------------------------------
例外発生時も必ず走らせたいコードは、finally 句に記述
-------------------------------------------------
def askint():
    # while ループを使わないと、チェックは一度だけになる
    while True:
        try:
            val = int(input("Please enter an integer: "))
        except:
            print("Looks like you did not enter an integer!")
            # continue のおかげで、このブロックに来たら、あらためて try ブロックに戻る
            continue
        else:
            print("Yep that's an integer!")
            break
        finally:
            print("Finally, I executed!")
        print(val)
-------------------------------------------------
try/except/finally 句では、
finally が評価されてようやく continue や break が評価されるため、
毎度 finally が評価されてしまう。

またprint(val) は try/except/finally 句 より後ろにあるため、
finally, continue, break は評価されても、print(val) は評価されない。
-------------------------------------------------

▼ ちょっと改善版
-------------------------------------------------
print(val) は評価される。finally は毎度評価されてしまう。
-------------------------------------------------
def askint():
    while True:
        try:
            val = int(input("Please enter an integer: "))
        except:
            print("Looks like you did not enter an integer!")
            continue
        else:
            print("Yep that's an integer!")
            # finally より、break よりも前に下記記述しないと、走らない
            print(val)
            break
        finally:
            print("Finally, I executed!")
-------------------------------------------------
