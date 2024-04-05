import FinanceDataReader as fdr
import pandas_datareader as web
import datetime as time
import ATO as db
import pandas as pd
import matplotlib.pyplot as plt
import os
from pykrx import stock

# df를 str으로 받아서, -100~100까지로 정규화된 df 반환
def normal(df, default='Close'):
    # Close 컬럼의 최대값과 최소값 계산
    max_close = df['Close'].max()
    min_close = df['Close'].min()
    # Close 값을 -100부터 100까지의 범위로 정규화하여 새로운 열 추가
    df['Close_normal'] = ((df['Close'] - min_close) / (max_close - min_close)) * 200 - 100
    return df

# 2개의 df와 filename을 받아서, plot 저장하고 corr 리턴
def saveplot(df,df2,filename):
    plt.plot(df.index, df['Close_normal'])
    plt.plot(df2.index, df2['Close_normal'])
    correlation=df['Close_normal'].corr(df2['Close_normal'])
    plt.title('Correlation is'+str(correlation))
    # 폴더가 없으면 생성
    save_dir = '../resources/static/img/plots'
    if not os.path.exists(save_dir):
        os.makedirs(save_dir)
    # 그래프를 'plots' 폴더에 그림 파일로 저장
    plt.savefig(os.path.join(save_dir, filename))
    plt.clf()
    return correlation

#df2개와 int를 받아서 int만큼 뒤로 당긴 df로 변환하고, 두 df의 격차 제거한 df 반환하는 함수
def delay_df(df1,df2,days):

    df1=df1.dropna()
    df2=df2.shift(-days)
    df2=df2.dropna()
    common_dates=df1.index.intersection(df2.index)
    df1=df1.loc[common_dates]
    df2=df2.loc[common_dates]
    return df1,df2

#6개 파라미터를 받아서, 파일 5개와 correlation 5개 반환
def cal_data(list,days=7):
    #파라미터 설정
    first_com=list[1]
#    print(first_com)
    first_db=list[2]
#    print(first_db)
    second_com=list[3]
#    print(second_com)
    second_db=list[4]
#    print(second_db)
    startdate=list[5]
#    print(startdate)
    lastdate=list[6]
#    print(lastdate)

    #data read
    connection=db.connect_to_oracle()
    df1=db.read_code_date(connection,first_db,first_com,startdate,lastdate)
    df1=df1.sort_index()
    df2=db.read_code_date(connection,second_db,second_com,startdate,lastdate)
    df2=df2.sort_index()
    #calculate data
    df1=normal(df1)
    print(df1.index.name)
    df2=normal(df2)
    print(df2.index.name)
    
    #1주씩 늦춰가며 비교 저장
    result_list=[]
    filename_list=[]
    for i in range(5):
        filename=first_com+'_'+first_com+'_'+startdate+'_'+lastdate+'_'+str(i)+'.png'
        result_list.append(saveplot(df1,df2,filename)) # save 및 파일명 저장
        df1,df2=delay_df(df1,df2,days)
        filename_list.append(filename)
    result_list.extend(filename_list)
    return result_list

list=['cal_data','1008','Index','IBM','SnP500','20120101','20130202']
print(cal_data(list))