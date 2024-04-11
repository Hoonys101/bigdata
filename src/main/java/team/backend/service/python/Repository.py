import json
import cx_Oracle

# JSON 데이터
json_data = '{"name": "John", "age": 30, "city": "New York"}'

# JSON을 파이썬 딕셔너리로 변환
data_dict = json.loads(json_data)

# DTO 클래스 정의
class PersonDTO:
    def __init__(self, name, age, city):
        self.name = name
        self.age = age
        self.city = city

# DTO 객체 생성
person = PersonDTO(data_dict['name'], data_dict['age'], data_dict['city'])

# Oracle에 연결
connection = cx_Oracle.connect("servlet", "java", "localhost:1521/java")

# 커서 생성
cursor = connection.cursor()

# DTO를 Oracle에 삽입하는 쿼리 실행
cursor.execute("INSERT INTO people (name, age, city) VALUES (:1, :2, :3)", (person.name, person.age, person.city))

# 변경사항 커밋
connection.commit()
print("오라클에 정보 입력 완료")
# 연결 종료
cursor.close()
connection.close()