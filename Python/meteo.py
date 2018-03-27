
PyPI の使い方
-------------------------------------------------
html リクエストのライブラリ
# pip install requests
# import requests

NASAページに HTTP リクエスト
# resp = requests.get('http://nasa.gov')

get した http のステータスコードを見る
# resp.status_code

get したテキストを表示
# resp.text
-------------------------------------------------

下記 NASA ページから JSON 取得
# 隕石が落ちた場所データ
# https://data.nasa.gov/Space-Science/Meteorite-Landings/gh4g-9sfh
# meteor_resp = requests.get('https://data.nasa.gov/resource/y77d-th95.json')

JSON を リスト内の Dictionary 形式に変換
# meteor_data = meteor_resp.json()
リスト内要素のデータタイプを確認してみる
# for meteor in meteor_data: print(type(meteor))

latlong ネットで緯度経度から場所を探す
# https://www.latlong.net/

▼ 緯度経度から距離をはかる
-------------------------------------------------
ラジアン
# 数学の角度を表す
# ラジアン ＝ 円（扇形）の孤の長さ(L)÷円の半径(r)
# 弧の長さ: 2π 半径:6
# ラジアン = 2π/6 = π/3[rad]
# 180° = π[rad]

2 セットの 緯度経度 から距離を測る
# import math
#
# def calc_dist(lat1, lon1, lat2, lon2):
#     lat1 = math.radians(lat1)
#     lon1 = math.radians(lon1)
#     lat2 = math.radians(lat2)
#     lon2 = math.radians(lon2)
#
#     h = math.sin((lat2 - lat1) / 2) ** 2 +\
#      math.cos(lat1) * \
#      math.cos(lat2) * \
#      math.sin( (lon2 - lon1) / 2 ) ** 2
#
#     return 6372.8 * 2 * math.asin(math.sqrt(h))

自宅の経緯を変数に格納
# my_loc = ( 35.028249, 135.769686)

Dictionary から 任意の value を取り出す
# meteor_data[0]['reclat']

for ループで、Dictionary の各要素に 新しい key/pair を追記
★ 距離計算結果を追記
# for meteor in meteor_data:
#     meteor['distance'] = calc_dist(float(meteor['reclat']), float(meteor['reclong']), my_loc[0], my_loc[1])

デバッグ: KeyError
# for meteor in meteor_data:
#     # print(meteor)
#     meteor['distance'] = calc_dist(float(meteor['reclat']), float(meteor['reclong']), my_loc[0], my_loc[1])

KeyError 回避策: 対象キーがない場合はスキップする場合
# for meteor in meteor_data:
#     if not ( 'reclat' in meteor and 'reclong' in meteor): continue
#     meteor['distance'] = calc_dist(float(meteor['reclat']), float(meteor['reclong']), my_loc[0], my_loc[1])

Dictionary から Key を指定して Value 取得ファンクション定義
デフォルト値は 浮動小数の正の無限大
# def get_dist(meteor):
#     return meteor.get('distance', math.inf)

List をソート時、引数に get_dist ファンクションを渡す
# meteor_data.sort(key=get_dist)

meteor_data リスト TOP 10 を表示
# meteor_data[0:10]

meteor_data リスト WORST 10 を表示
# 最後尾開始 後ろから10番目終了 順ステップ →最後尾から順になるので、0件
# meteor_data[-1:-11]
# 最後尾開始 後ろから10番目終了 逆ステップ →最後尾から逆順にステップしていくのでワースト10
# meteor_data[-1:-11:-1]

既存リスト を操作して、新たなリスト作成出力
条件: 要素のキーに 'distance' を含まない
# # for の処理 →条件式処理に合致したら [] に保持 →先頭の m が、リストに格納される dictionary を指している？
#  [ m for m in meteor_data if 'distance' not in m ]
# # 先頭の m がないとエラー
#  [for m in meteor_data if 'distance' not in m ]
-------------------------------------------------
