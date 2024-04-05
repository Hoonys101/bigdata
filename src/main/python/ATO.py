import cx_Oracle
import pandas as pd
import datetime as time

# Oracle 연결 정보 전역 변수로 설정
USERNAME = "bigdata"
PASSWORD = "java"
HOST = "localhost:1521/java"

# 테이블 이름 변수화(people table이 예시)
TABLE_NAME = "stock"

# Oracle에 연결하는 함수
def connect_to_oracle():
    connection = cx_Oracle.connect(USERNAME, PASSWORD, HOST)
    return connection

#str으로 8자리 str을 받아서 /로 구분되는 문자열 생성
def str_to_date(str_date='20121212'): 
#    str_date='20121212'
    print(str_date)
    year=str_date[0:4]
    month=str_date[4:6]
    day=str_date[6:8]
    return year+'/'+month+'/'+day

# Insert 기능을 수행하는 함수<-- 삭제
def create_person_from_dataframe(connection, df):
    cursor = connection.cursor()
    columns = ', '.join(df.columns)
    placeholders = ', '.join([':' + str(i+1) for i in range(len(df.columns))])
    query = f"INSERT INTO {TABLE_NAME} ({columns}) VALUES ({placeholders})"
    for _, row in df.iterrows():
        cursor.execute(query, tuple(row))
    connection.commit()
    cursor.close()

# connection, 날짜1,2(8자리 str), 테이블명을 받고 Read 기능을 수행하는 함수 <-- 테이블명 필요 없음, 기업명 인자 필요
def read_date(connection,start='20120101',last='20220101',TABLE_NAME='archiveddata'):
    cursor = connection.cursor()
    start=str_to_date(start)
    last=str_to_date(last)
    print(start, last)
    try:
        query = f"SELECT * FROM {TABLE_NAME} WHERE data_date >= TO_DATE(:1, 'YYYY/MM/DD') AND data_date <= TO_DATE(:2,'YYYY/MM/DD')"
        cursor.execute(query,(start,last))
        result = cursor.fetchall()
        if result:
            # 판다스 DataFrame으로 변환
            columns = [desc[0] for desc in cursor.description]
            df = pd.DataFrame(result, columns=columns)
            print(df)
            if 'data_date' in df.columns:
                df.set_index('data_date', inplace=True)
            return df
        else:
            print("No data found for the given name.")
    finally:
        cursor.close()
        
# connection, 날짜1,2(8자리 str), 테이블명을 받고 Read 기능을 수행하는 함수 <-- 테이블명 필요 없음, 기업명 인자 필요
def read_code_date(connection,db_name='KRX',stock_code='005930',start='20120101',last='20220101',TABLE_NAME='archiveddata'):
    cursor = connection.cursor()
    start=str_to_date(start)
    last=str_to_date(last)
    print(start, last)
    try:
        query = f"SELECT * FROM {TABLE_NAME} WHERE db_name=:1 AND stock_code=:2 AND data_date >= TO_DATE(:3, 'YYYY/MM/DD') AND data_date <= TO_DATE(:4,'YYYY/MM/DD')"
        cursor.execute(query,(db_name,stock_code,start,last))
        result = cursor.fetchall()
        if result:
            # 판다스 DataFrame으로 변환
            columns = [desc[0] for desc in cursor.description]
            df = pd.DataFrame(result, columns=columns)
            print(df)
#            if 'Date' in df.columns:
            df.set_index('DATA_DATE', inplace=True)
            return df
        else:
            print("No data found for the given name.")
    finally:
        cursor.close()

# connection, DB명, code, table명을 받아서 df 출력
def read_db_code(connection,data_base='KRX',code='005930',TABLE_NAME='archiveddata'):
    cursor = connection.cursor()
    try:
        query = f"SELECT * FROM {TABLE_NAME} WHERE data_base = :1 AND stock_code = :2"
        cursor.execute(query,(data_base,code))
        result = cursor.fetchall()
        if result:
            # 판다스 DataFrame으로 변환
            columns = [desc[0] for desc in cursor.description]
            df = pd.DataFrame(result, columns=columns)
            print(df)
            return df
        else:
            print("No data found for the given name.")
    finally:
        cursor.close()

# 연결을 종료하는 함수
def close_connection(connection):
    connection.close()

# 날짜는 날짜로, 숫자 및 문자열은 문자열로 출력
def format_value(value):
    if isinstance(value, pd.Timestamp):  # 날짜 형식인 경우
        return f"TO_DATE('{value.strftime('%Y-%m-%d %H:%M:%S')}', 'YYYY-MM-DD HH24:MI:SS')"
    elif isinstance(value, (int, float)):  # 숫자 형식인 경우
        return str(value)
    else:  # 그 외의 경우는 문자열로 변환
        return f"'{value}'"

# connection, df, 테이블 이름을 받아서 테이블 생성
def create_table_from_dataframe(connection, df, table_name):
    try:
        # Oracle 연결
        cursor = connection.cursor()

        # DataFrame의 인덱스를 임시로 해제
        if 'data_date' in df.columns:
            df.reset_index(inplace=True)
#        df.reset_index(inplace=True)

        # DataFrame의 속성을 기반으로 테이블 생성
        create_table_query = f"CREATE TABLE {table_name} ("
        for column in df.columns:
            if df[column].dtype == 'datetime64[ns]':  # 날짜 형식인 경우
                create_table_query += f"\"{column}\" DATE, "  # DATE 형식으로 지정
            elif df[column].dtype == 'int64' or df[column].dtype == 'float64':  # 숫자 형식인 경우
                create_table_query += f"\"{column}\" NUMBER, "  # NUMBER 형식으로 지정
            else:
                create_table_query += f"\"{column}\" VARCHAR2(255), "  # 기본적으로 VARCHAR2(255)로 생성

        create_table_query = create_table_query[:-2] + ")"  # 마지막 쉼표 제거
        print("쿼리문은 "+create_table_query)
        cursor.execute(create_table_query)

        # 변경사항 커밋
        connection.commit()
        print(f"{table_name} 테이블 생성 완료")

        # DataFrame의 인덱스를 다시 설정
        if 'Date' in df.columns:
            df.set_index('Date', inplace=True)

    except Exception as e:
        print(f"테이블 생성 중 오류 발생: {e}")

    finally:
        cursor.close()

#connection, df, table_name을 받아서 DB에 내용 저장
def insert_data_to_table(connection,df, table_name='ArchivedData'):
    try:
        # Oracle 연결
        cursor = connection.cursor()
        # DataFrame의 인덱스를 임시로 해제
#        if 'Date' in df.columns:
#            df.reset_index(inplace=True)
        df.reset_index(inplace=True)
        #DataFrame의 nan 값을 ""로 수정
        df.fillna('null',inplace=True)
        # DataFrame의 데이터를 테이블에 입력하는 쿼리 실행
        insert_query = f"INSERT INTO {table_name} VALUES ("
        for _, row in df.iterrows():
            values = ", ".join([format_value(value) for value in row])
#            print(insert_query + values + ")")
            cursor.execute(insert_query + values + ")")

        # 변경사항 커밋
        connection.commit()
        print(f"{len(df)}건의 데이터를 {table_name} 테이블에 입력 완료")

        # DataFrame의 인덱스를 다시 설정
        if 'data_date' in df.columns:
            df.set_index('data_date', inplace=True)

    except Exception as e:
        print(f"데이터 입력 중 오류 발생: {e}")

    finally:
        cursor.close()
        
def insert_data_menu_to_table(connection,df, table_name):
    try:
        # Oracle 연결
        cursor = connection.cursor()
        # DataFrame의 인덱스를 임시로 해제
        if 'Date' in df.columns:
            df.reset_index(inplace=True)
#        df.reset_index(inplace=True)
        #DataFrame의 nan 값을 ""로 수정
        df.fillna('null',inplace=True)
        # DataFrame의 데이터를 테이블에 입력하는 쿼리 실행
        insert_query = f"INSERT INTO {table_name} VALUES ("
        for _, row in df.iterrows():
            values = ", ".join([format_value(value) for value in row])
#            print(insert_query + values + ")")
            cursor.execute(insert_query + values + ")")

        # 변경사항 커밋
        connection.commit()
        print(f"{len(df)}건의 데이터를 {table_name} 테이블에 입력 완료")

        # DataFrame의 인덱스를 다시 설정
        if 'Date' in df.columns:
            df.set_index('Date', inplace=True)

    except Exception as e:
        print(f"데이터 입력 중 오류 발생: {e}")

    finally:
        cursor.close()