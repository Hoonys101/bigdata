import FinanceDataReader as fdr
import pandas_datareader as web
import datetime as time
import DAO as db
import pandas as pd
import matplotlib.pyplot as plt
import os
from pykrx import stock
import threading
#from pykrx import bond

# DB를 list로 받아서, 각 DB의 column명을 list의 list로 반환 depressed
def stocklisting(strs=['KOSPI']):
    results=[]
    for str in strs:
        result=fdr.StockListing(str)
        result.insert(loc=0, column="data_base", value=str)
        results.append(result)
    transfered_results=pd.concat(results,ignore_index=True)
    return transfered_results

# 시작 날짜, 끝 날짜를 8자리 str으로 받고, 검색 대상을 str으로 받아서(한국주식은 6자리 코드) df로 반환 depressed
def stockprice(db_name='', code='005930', startdate='20120101', lastdate='20220101'):
    if db_name=='Index':
        df=stock.get_index_ohlcv(startdate, lastdate, code)[['시가','고가','저가','종가','거래량']]
        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume'},inplace=True)
        df.rename_axis('data_date',inplace=True)
    else:
        df = fdr.DataReader(code, startdate,lastdate)[['Open','High','Low','Close','Volume']]
        df.rename_axis('data_date',inplace=True)
    return df
# Sector, Code를 받아서 웹으로부터 20120101~20220101 데이터의 필요 컬럼을 선정하여 df로 반환
def stock_data(db_name='sdfklj', stock_code='IBM', startdate='20120101',lastdate='20220101', default='Close'):
    if db_name=='Index':
#        df=stock.get_index_ohlcv(startdate, lastdate, stock_code)[['시가','고가','저가','종가','거래량','등락률']]
#        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume','등락률':'various'},inplace=True)
        df=stock.get_index_ohlcv(startdate, lastdate, stock_code)[['시가','고가','저가','종가','거래량']]
        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume'},inplace=True)
#        df['various']=0
        df.rename_axis('data_date',inplace=True)
        
    else:
        # print(stock_code,startdate,lastdate)
#        df = fdr.DataReader(stock_code, startdate,lastdate)[['Open','High','Low','Close','Volume','Change']]
#        df.rename(columns={'Change':'various'},inplace=True)
        df = fdr.DataReader(stock_code, startdate,lastdate)[['Open','High','Low','Close','Volume']]
        df.rename_axis('data_date',inplace=True)
    data_processing(df,db_name,stock_code)
    connect=db.connect_to_oracle()
    db.insert_data_to_table(connect,df)
    db.close_connection(connect)

def stock_data2(db_name='sdfklj', stock_code='IBM', startdate='20120101',lastdate='20220101', default='Close'):
    if db_name=='Index':
#        df=stock.get_index_ohlcv(startdate, lastdate, stock_code)[['시가','고가','저가','종가','거래량','등락률']]
#        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume','등락률':'various'},inplace=True)
        df=stock.get_index_ohlcv(startdate, lastdate, stock_code)[['시가','고가','저가','종가','거래량']]
        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume'},inplace=True)
#        df['various']=0
        df.rename_axis('data_date',inplace=True)
        
    else:
        # print(stock_code,startdate,lastdate)
#        df = fdr.DataReader(stock_code, startdate,lastdate)[['Open','High','Low','Close','Volume','Change']]
#        df.rename(columns={'Change':'various'},inplace=True)
        df = fdr.DataReader(stock_code, startdate,lastdate)[['Open','High','Low','Close','Volume']]
        df.rename_axis('data_date',inplace=True)
    return data_processing(df,db_name,stock_code)

# dataFrame, str DB명, stock_code를 받아서, dataFrame에 stock_code,db_name가 추가된 df로  stock_data의 딸림
def data_processing(df,db_name,stock_code):
        df.insert(loc=0,column="db_name",value=db_name)
        df.insert(loc=0,column="stock_code",value=stock_code)
        return df



# 8자리 날짜 str을 datetime으로 반환
def str_to_date(str_date='20121212'):
    year=int(str_date[0:4])
    month=int(str_date[4:6])
    day=int(str_date[6:8])
    return time.datetime(year,month,day)

# (fred에서) 시작날짜, 끝날짜를 8자리 str으로 받고, 검색 대상을 str으로 받아서 df로 반환//원자재 검색
def row_price_date(startdate='20150101',enddate='20220101',subject='DCOILWTICO'):
    start=str_to_date(startdate)
    end=str_to_date(enddate)
    resource='fred'
    result= web.DataReader(subject,resource,start,end)
    return result


#Sector와 Code를 입력받아서, 동일한 코드 생성
def add_data(list=[])->str:
    
    db_name=list[1]
    stock_code=list[2]
    # print(db_name,stock_code)
#    df=stockprice(db_name,stock_code)
    # print('db_name: ',db_name,' stock_code:', stock_code)
    stock_data_all(db_name,stock_code)
    return "추가했습니다."
    # print(df)
    
#    db.create_table_from_dataframe(connect,refine_df,"ArchivedData")

def stock_data_all(db_name='sdfklj',stock_code='IBM'):
    lst=[]
    #쓰레드 생성
    for i in range(12,22):
        startdate='20'+str(i)+'0101'
        lastdate='20'+str(i+1)+'0101'
        lst.append(threading.Thread(target=stock_data,args=(db_name,stock_code,startdate,lastdate)))
    #쓰레드 실행
    for i in lst:
        i.start()
    #메인 쓰레드에서 대기
    for i in lst:
        i.join()


# add_data(['','Index','1008'])
# add_data(['','SnP500','IBM'])

# insertdata=[
#     '1152',
#     '1160',
#     '1153',
#     '1154',
#     '1001',
#     '1020',
#     '1011',
#     '1159',
#     '1008',
#     '1034',
#     '1155']
# for stt in insertdata:
#     print(stt,"입력중")
#     add_data(['','Index',stt])
#     print(stt,'입력완료')
# inndata=['026960',
#     'IBM',
#     '012330',
#     '058650',
#     '058430',
#     '004020',
#     '004100',
#     '063160']
# for stt in inndata:
#     print(stt,"입력중")
#     add_data(['','erg',stt])
#     print(stt,'입력완료')