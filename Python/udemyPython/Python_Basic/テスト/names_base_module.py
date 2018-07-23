from テスト対象モジュール import get_formatted_name

print("'q' を入力すると、いつでも終了できます")
while True:
    first = input("\nPlease give me a first name: ")
    if first == 'q':
        break
    last = input("\nPlease give me a last name: ")
    if last == 'q':
        break

    formatted_name = get_formatted_name(first, last)
    print("フォーマットされた名前: " + formatted_name + ".")
