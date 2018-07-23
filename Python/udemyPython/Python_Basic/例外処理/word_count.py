def count_words(filename):
    """ファイル内の単語の数をカウントして出力する"""
    try:
        # Windows だとデフォルトが utf-8 にならずえらーになるため、回避策
        with open(filename,encoding="utf-8") as f_obj:
            contents = f_obj.read()
    except FileNotFoundError:
        # msg = filename + "というファイルが見つかりません"
        # print(msg)
        pass # エラーになってもなにもしない
    else:
        words = contents.split()
        num_words = len(words)
        print(filename + "には" + str(num_words) + "の単語が含まれています。")

filenames = ['TheBrothersKaramazov.txt', 'InuYasha.txt', 'MobyDick.txt']
for filename in filenames:
    count_words(filename)
