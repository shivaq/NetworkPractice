import sys
import file1

# [0] は、対象ファイル file3.py に対応している
num = int(sys.argv[1])
print(file1.myfunc(num))