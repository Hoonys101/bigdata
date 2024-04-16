import ATO as db
import pandas as pd
import matplotlib.pyplot as plt
import os



# df를 str으로 받아서, -100~100까지로 정규화된 df 반환
def normal(df, default='CLOSE'):
    # Close 컬럼의 최대값과 최소값 계산
    max_close = df['CLOSE'].max()
    min_close = df['CLOSE'].min()
    # Close 값을 -100부터 100까지의 범위로 정규화하여 새로운 열 추가
    df['Close_normal'] = ((df['CLOSE'] - min_close) / (max_close - min_close)) * 200 - 100
    return df[['Close_normal']]

# 정규화된 2개의 df를 받아서, 차를 계산한 df 반환
def diff(df1, df2):
    # 두 DataFrame의 인덱스와 열이 일치하도록 정렬
    df1 = df1.sort_index(axis=0).sort_index(axis=1)
    df2 = df2.sort_index(axis=0).sort_index(axis=1)

    # 차분 연산 수행
    diff_df = df1.subtract(df2, fill_value=0)  # 누락된 값은 0으로 대체

    return diff_df


# 2개의 df와 filename을 받아서, plot 저장하고 corr 리턴
def saveplot(df,df2,filename):
    plt.plot(df.index, df['Close_normal'])
    plt.plot(df2.index, df2['Close_normal'])
    correlation=df['Close_normal'].corr(df2['Close_normal'])
    plt.title('Correlation is'+str(correlation))
    # 폴더가 없으면 생성
#    save_dir = './src/main/resources/static/img/plots'
    save_dir = 'c:/plots/'
    if not os.path.exists(save_dir):
#        print("폴더가 없어서 새로 만듭니다.")
        os.makedirs(save_dir)
    # 그래프를 'plots' 폴더에 그림 파일로 저장
    plt.savefig(os.path.join(save_dir, filename))
#    print("파일이 저장된 경로:", os.path.abspath(save_dir))
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

#7-1개의 파라미터를 받아서 파일 5개와 correlation 5개 반환
def diff_cal_data(list,days=5):
    #파라미터 설정
    first_com=list[1]
    diff1_com=list[2]
    second_com=list[3]
    diff2_com=list[4]
    startdate=list[5]
    lastdate=list[6]

    #data read
    connection=db.connect_to_oracle()
    df1=db.read_code_date(connection,first_com,startdate,lastdate)
    df1=df1.sort_index()
    df1_1=db.read_code_date(connection,diff1_com,startdate,lastdate)
    df1_1=df1_1.sort_index()
    df2=db.read_code_date(connection,second_com,startdate,lastdate)
    df2=df2.sort_index()
    df2_1=db.read_code_date(connection,diff2_com,startdate,lastdate)
    df2_1=df2_1.sort_index()
    #calculate data
    df1=normal(df1)
    df1_1=normal(df1_1)
    df1=diff(df1,df1_1)
    df2=normal(df2)
    df2_1=normal(df2_1)
    df2=diff(df2,df2_1)

    #1주씩 늦춰가며 비교 저장
    result_list=[]
    filename_list=[]
    for i in range(5):
        filename=first_com+'_'+second_com+'_'+startdate+'_'+lastdate+'_'+str(i)+'.png'
        result_list.append(saveplot(df1,df2,filename)) # save 및 파일명 저장
        df1,df2=delay_df(df1,df2,days)
        filename_list.append(filename)
    result_list.extend(filename_list)
    connection.close()
    return result_list



#5-1개 파라미터를 받아서, 파일 5개와 correlation 5개 반환
def cal_data(list,days=5):
    #파라미터 설정
    first_com=list[1]
    second_com=list[2]
    startdate=list[3]
    lastdate=list[4]
    print(first_com,second_com,startdate,lastdate)
    #data read
    connection=db.connect_to_oracle()
    df1=db.read_code_date(connection,first_com,startdate,lastdate)
    df1=df1.sort_index()
    df2=db.read_code_date(connection,second_com,startdate,lastdate)
    df2=df2.sort_index()
    #calculate data
    df1=normal(df1)
    df2=normal(df2)

    #1주씩 늦춰가며 비교 저장
    result_list=[]
    filename_list=[]
    for i in range(5):
        filename=first_com+'_'+second_com+'_'+startdate+'_'+lastdate+'_'+str(i)+'.png'
        result_list.append(saveplot(df1,df2,filename)) # save 및 파일명 저장
        df1,df2=delay_df(df1,df2,days)
        filename_list.append(filename)
    result_list.extend(filename_list)
    connection.close()
    return result_list