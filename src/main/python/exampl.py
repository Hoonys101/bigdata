import pandas as pd
import ATO
from io import StringIO
import json_to_df as jtd

# 다른 Oracle 연결 정보로 재설정 가능
    #db_operations.USERNAME = "other_username"
    #db_operations.PASSWORD = "other_password"
    #db_operations.HOST = "other_host:1521/other_service"

#JSON 데이터를 파일로부터 읽기



# JSON 데이터를 DataFrame으로 변환
json_data = '{"name": ["John", "Alice"], "age": [30, 25], "city": ["New York", "Los Angeles"]}'
df = pd.read_json(StringIO(json_data))

# Oracle에 연결
connection = ATO.connect_to_oracle()

# DataFrame을 Oracle에 삽입
ATO.create_person_from_dataframe(connection, df)

# 특정 이름의 데이터 읽기
result = ATO.read_person(connection,"John")
print("Read Result:")
print(result)

# 연결 종료
ATO.close_connection(connection)