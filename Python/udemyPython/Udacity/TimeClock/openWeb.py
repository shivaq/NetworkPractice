import time
import webbrowser

total_count = 3
count = 1

while(count <= total_count):
    time.sleep(10)
    webbrowser.open("https://www.youtube.com/watch?v=8wbi9q-tV8Q")
    # ++ はサポートしてない
    count+= 1
else:
    print("Done!")
