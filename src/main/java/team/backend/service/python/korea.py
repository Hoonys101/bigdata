import pandas as pd
import requests

# API 엔드포인트 URL
url = "https://ecos.bok.or.kr/api/StatisticItemList/{}/json/kr/1/1000/036Y001/MM/19900101/20231231".format("5I4VZKT4FOPEFSN5110C")

# API 요청 보내기
response = requests.get(url, verify=False) 

# 응답 확인
if response.status_code == 200:
    # JSON 데이터 추출
    data = response.json()
    
    # JSON 데이터를 DataFrame으로 변환
    df = pd.DataFrame(data['StatisticItemList']['row'])
    
    # 데이터 확인
    print(df.head())
else:
    print("API 요청이 실패했습니다. 상태 코드:", response.status_code)