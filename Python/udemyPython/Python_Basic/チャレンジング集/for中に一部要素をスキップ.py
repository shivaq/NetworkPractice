▼ For ループ内 2段階 while
-------------------------------------------------
要件
# ・引数の配列のSum を返す
# ・要素6 がヒットしたら、次の 9 までの要素はSum から除外
-------------------------------------------------
def summer_69(array):
    total = 0
    do_add = True
    for num in array:
        while do_add:
            if num != 6:
                total += num
                break
            else:
                do_add = False
        while not do_add:
            if num != 9:
                break
            else:
                do_add = True
                break
    return total



# Check
summer_69([1, 3, 5])
-------------------------------------------------
