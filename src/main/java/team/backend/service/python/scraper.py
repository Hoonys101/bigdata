import FinanceDataReader as fdr
import pandas_datareader as web
import datetime as time
import ATO as db
import pandas as pd
import matplotlib.pyplot as plt
import os
from pykrx import stock
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
def stock_data(stock_code='IBM', db_name='sdfklj', startdate='20120101',lastdate='20220101', default='Close'):
    if db_name=='Index':
        df=stock.get_index_ohlcv(startdate, lastdate, stock_code)[['시가','고가','저가','종가','거래량']]
        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume'},inplace=True)
        df.rename_axis('data_date',inplace=True)
        
    else:
        df = fdr.DataReader(stock_code, startdate,lastdate)[['Open','High','Low','Close','Volume']]
        df.rename_axis('data_date',inplace=True)
    data_processing(df,db_name,stock_code)
    return df

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

# Sector, Code와 날짜 2개를 str으로 받아서, -100~100까지로 정규화된 df 반환
'''def normal(code='IMO', db_name='sdfklj', startdate='20150101',lastdate='20220101', default='Close'):
    if db_name=='Index':
        df=stock.get_index_ohlcv(startdate, lastdate, code)[['시가','고가','저가','종가','거래량']]
        df.rename(columns={'시가':'Open','고가':'High','저가':'Low','종가':'Close','거래량':'Volume'},inplace=True)
        df.rename_axis('Date',inplace=True)
    else:
        df = fdr.DataReader(code, startdate,lastdate)
'''
def normal(df, default='Close'):
    # Close 컬럼의 최대값과 최소값 계산
    max_close = df[default].max()
    min_close = df[default].min()
    # Close 값을 -100부터 100까지의 범위로 정규화하여 새로운 열 추가
    df['Close_normal'] = ((df['Close'] - min_close) / (max_close - min_close)) * 200 - 100
    return df

# 2개의 df와 filename을 받아서, plot 저장하고 corr 리턴
def saveplot(df,df2,filename):
    plt.plot(df.index, df['Close_normal'])
    plt.plot(df2.index, df2['Close_normal'])
    correlation=df['Close_normal'].corr(df2['Close_normal'])
    plt.title('Correlation between "IMO" and "CBOE" is'+str(correlation))
    # 폴더가 없으면 생성
    save_dir = '../resources/static/img/plots'
    if not os.path.exists(save_dir):
        os.makedirs(save_dir)
    # 그래프를 'plots' 폴더에 그림 파일로 저장
    plt.savefig(os.path.join(save_dir, filename))
    plt.clf()
    return correlation

# 기업명,name명 X 2개와 날짜 2개를 str으로 받고, 당기는 날짜를 int로 받아서 날짜 당기기 및 비교 5회 반복하며 plot 저장하고, corr 리턴
def cal_corr(com1='IMO',sector1="sdfsdf",com2='CBOE',sector2="wefwef",startdate='20150101',lastdate='20220101', days=30):
    df1=normal(com1,sector1,startdate,lastdate)
    df2=normal(com2,sector2,startdate,lastdate)
    list=[]
    for i in range(5):
        filename=com1+'_'+com2+'_'+startdate+'_'+lastdate+'_'+str(i)+'.png'
        list.append(saveplot(df1,df2,filename)) # save 및 파일명 저장
        df1,df2=delay_df(df1,df2,days)
    return list
#   return saveplot(df1,df2,filename)

#df2개와 int를 받아서 int만큼 뒤로 당긴 df로 변환하고, 두 df의 격차 제거한 df 반환하는 함수
def delay_df(df1,df2,days):
    df1=df1.iloc[:-days]
    df2=df2.shift(-days)
    df2=df2.dropna()
    return df1,df2

#Sector와 Code를 입력받아서, 동일한 코드 생성
def add_data(list=[]):
    stock_code=list[1]
    db_name=list[2]
    print(db_name,stock_code)
#    df=stockprice(db_name,stock_code)
    df=stock_data(stock_code,db_name)
    connect=db.connect_to_oracle()
#    db.create_table_from_dataframe(connect,refine_df,"ArchivedData")
    db.insert_data_to_table(connect,df)
    db.close_connection(connect)


