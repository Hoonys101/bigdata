import DAO as db
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os
import ai
# import concurrent.futures
#df 전체에 대해 1차 변화율과 2차 변화율을 추가
def differ(df:pd.DataFrame,default='CLOSE')->pd.DataFrame:
    #Close 값을 기준으로 변화율 계산 및 새로운 열 추가
    default_index=df.columns.get_loc(default)
    diff_values=[]
    for i in range(0,df.shape[0]-1):
        diff=(df.iloc[i+1,default_index]-df.iloc[i,default_index])/df.iloc[i,default_index]*100
        diff_values.append(diff)
#        print(diff)
    diff_values.append(float('NaN'))
    df['Diff']=diff_values
    return df
def diff_diff(df:pd.DataFrame,default='Diff')->pd.DataFrame:
    #Close 값을 기준으로 변화율 계산 및 새로운 열 추가
    default_index=df.columns.get_loc(default)
    diff_values=[]
    for i in range(0,df.shape[0]-1):
        diff=(df.iloc[i+1,default_index]-df.iloc[i,default_index])/df.iloc[i,default_index]*100
        diff_values.append(diff)
#        print(diff)
    diff_values.append(float('NaN'))
    df['Diff_diff']=diff_values
    return df

# DB의 Stock_code를 입력받아서, 1,2차 도함수를 생성하여 반환
def make_diff(stock_code:str='005810')->pd.DataFrame:
    df=make_df(stock_code)
    return diff_diff(differ(df))

#make_df stock_code를 입력받아서 df 반환
def make_df(stock_code:str='005810')->pd.DataFrame:
    connection=db.connect_to_oracle()
    df=db.read_code_date(connection,stock_code)
    df=df.sort_index()
    connection.close()
    return df


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
def saveplot(df,df2,filename,column:str='Close_normal'):
    plt.plot(df.index, df[column])
    plt.plot(df2.index, df2[column])
    correlation=df[column].corr(df2[column])
    correlation=f"{correlation:.4f}"
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
def delay_df(df1,df2,days=5):

    df1=df1.dropna()
    df2=df2.shift(-days)
    df2=df2.dropna()
    common_dates=df1.index.intersection(df2.index)
    df1=df1.loc[common_dates]
    df2=df2.loc[common_dates]
    return df1,df2

# 3-1개의 파라미터를 받아서 2개의 str 반환
def ai_anal(list):
    #파라미터 설정
    first_com=list[1]
    second_com=list[2]
    ai.training(first_com,second_com)

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
    # print(first_com,second_com,startdate,lastdate)
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


def delay_save(df1:pd.DataFrame,df2:pd.DataFrame,name,days:int=5)->str:
    result_list=[]
    startdate=df1.index[0].date()
    lastdate=df1.index[-1].date()
    for i in range(5):
        result_list.append(df1.corr(df2))
        df1,df2=delay_df(df1,df2,days)
    max_val,max_inde=find_max_and_index(result_list)
    if max_val<0.7:
#        print("관련이 없습니다.")
        pass
#    elif max_inde==0:
#        print(startdate.date(),"와 " ,lastdate.date(),"사이의 기간은 동시에 연동됩니다.\n max_corr: ",max_val)
#        print(df1.index[startdate].date()+','+df1.index[lastdate].date()+','+str(max_inde))
#    elif max_inde==4:
#        print(startdate.date(),"와 " ,lastdate.date(),"사이의 기간은 4주 이상의 간격으로 연동됩니다.\n max_corr: ",max_val)
    else:
#        print(startdate.date(),"와 " ,lastdate.date(),'사이의 기간은 '+str(max_inde)+'주 간격으로 연동됩니다.\n max_corr: ',max_val)
# 결과에 출력
#        print(str(startdate)+','+str(lastdate)+','+str(max_inde))
        return str(startdate)+','+str(lastdate)+','+str(max_inde)
    
def find_max_and_index(lst:list=[1,2,3,2,1])->tuple[int,int]:
    if not lst:  # 리스트가 비어있는 경우
        return None, None
    max_value = max(lst)
    max_index = lst.index(max_value)
    return max_value, max_index

# #5-1개 파라미터를 받아서, 파일 5개와 correlation 5개 반환
# #멀티 쓰레드 버전
# def cal_data(list,days=5):
#     #파라미터 설정
#     first_com=list[1]
#     second_com=list[2]
#     startdate=list[3]
#     lastdate=list[4]
#     # print(first_com,second_com,startdate,lastdate)
#     #data read
#     connection=db.connect_to_oracle()
#     df1=db.read_code_date(connection,first_com,startdate,lastdate)
#     df1=df1.sort_index()
#     df2=db.read_code_date(connection,second_com,startdate,lastdate)
#     df2=df2.sort_index()
#     #calculate data
#     df1=normal(df1)
#     df2=normal(df2)

#     #1주씩 늦춰가며 비교 저장
#     result_list=[]
#     filename_list=[]
#     with concurrent.futures.ThreadPoolExecutor() as executor:
        
#         # 쓰레드 생성 및 실행
#         future_result = []
#         for i in range(5):
#             filename=first_com+'_'+second_com+'_'+startdate+'_'+lastdate+'_'+str(i)+'.png'
#             filename_list.append(filename)
#             future = executor.submit(saveplot, df1,df2,filename)  # name 파라미터 전달
#             future_result.append(future)
#             df1,df2=delay_df(df1,df2,days)

#         # 각 쓰레드의 결과를 취합
#         result_list = [future.result() for future in future_result]
#     result_list.extend(filename_list)
#     connection.close()
#     print(result_list)
#     return result_list

# # df1=make_diff('1008').loc['2012-01-01':'2013-02-02','Diff']
# # df2=make_diff('IBM').loc['2012-01-01':'2013-02-02','Diff']
# df1=make_df('1008').loc['2012-01-01':'2013-02-02','CLOSE']
# df2=make_df('IBM').loc['2012-01-01':'2013-02-02','CLOSE']
# df1=make_df('1008')['CLOSE']
# df2=make_df('IBM')['CLOSE']
# # df1=make_diff('1008')
# # df2=make_diff('IBM')
# # print(df1)
# # print(df2)
# for i in range(10):
#     correlation = df1.corr(df2)
#     print(correlation)
#     df1,df2=delay_df(df1,df2)


def common_date(df1:pd.DataFrame,df2:pd.DataFrame)->tuple[pd.DataFrame,pd.DataFrame]:
    common_dates=df1.index.intersection(df2.index)
    df1=df1.loc[common_dates]
    df2=df2.loc[common_dates]
    return df1,df2
    
# 1년을 4개월씩 분석
def yearly(df1:pd.DataFrame,df2:pd.DataFrame,div:int=4,critic:float=0.7)->list:
    result=[]
    for i in range(div):
        startdate=int(df1.shape[0]/div*i)
        lastdate=int(df1.shape[0]/div*(i+1))
        correlation = df1[startdate:lastdate].corr(df2[startdate:lastdate])
        if correlation>critic:
#            print(df1.index[startdate].date(),"와 " ,df1.index[lastdate-1].date(), " 사이의 분기에 양의 상관관계")
#            print("correlation: ",correlation)            
            result.append(delay_save(df1[startdate:lastdate],df2[startdate:lastdate],str(startdate)+'_'+str(lastdate)))
#        elif correlation<-critic:
#            print(df1.index[startdate].date(),"와 ",df1.index[lastdate-1].date()," 사이의 분기에 음의 상관관계")
#            print("correlation: ",correlation)
#            delay_save(df1[startdate:lastdate],df2[startdate:lastdate],str(startdate)+'_'+str(lastdate))
#        else:
#            print(df1.index[startdate].date(),"와 ",df1.index[lastdate-1].date()," 사이의 기간에 관계없음")
#            print("correlation: ",correlation)
#            delay_save(df1[startdate:lastdate],df2[startdate:lastdate],str(startdate)+'_'+str(lastdate))

#list 0,1,2를 받아서 total_anal 실행
def total_analy(lst:list)->list:
    stock_code1=lst[1]
    stock_code2=lst[2]
    return total_anal(stock_code1,stock_code2)
# 10 년 전체를 년별 분석
def total_anal(stock_code1:str='1008',stock_code2:str='IBM',yearly=yearly,default:str='CLOSE')->list:
    result=[]
    df1=make_df(stock_code1)[default]
    df2=make_df(stock_code2)[default]
    df1,df2=common_date(df1,df2)
    for i in range(10):
        startdate=int(df1.shape[0]/10*i)
        lastdate=int(df1.shape[0]/10*(i+1))
        correlation = df1[startdate:lastdate].corr(df2[startdate:lastdate])
        if correlation>0.5:
#            print(df1.index[startdate].date(),"와 " ,df1.index[lastdate].date(), " 사이의 기간에 연간 양의 상관관계가 있습니다.")
#            print("correlation: ",correlation)            
            result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))
            
        elif correlation<-0.5:
#            print(df1.index[startdate].date(),"와 ",df1.index[lastdate].date()," 사이의 기간에 연간 음의 상관관계가 있습니다.")
#            print("correlation: ",correlation)
            result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))
        else:
#            print(df1.index[startdate-1].date(),"와 ",df1.index[lastdate-1].date()," 사이의 기간에 상관관계가 적습니다.")
#            print("correlation: ",correlation)
            result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))

    # df1,df2=delay_df(df1,df2)
    return result

# df1=make_diff('1008')
# df2=make_diff('IBM')
# for i in range(5):
#     correlation = df1.loc['2012-01-01':'2013-02-02','Diff'].corr(df2.loc['2012-01-01':'2013-02-02','Diff'])
#     print(correlation)
#     df1,df2=delay_df(df1,df2)
# lst=['find_period','023440','1153']
# total_analy(lst)

def normalNlabel(stock_code1:str='IBM',stock_code2:str='1008',default='CLOSE')->pd.DataFrame:
    df1=make_df(stock_code1)[[default]]
    df2=make_df(stock_code2)[[default]]
    # print("df1: \n",df1)
    # print("df2: \n",df2)
    df1,df2=common_date(df1,df2)
    df1=normal(df1)
    df2=normal(df2)
    # print("df1: \n",df1)
    # print("df2: \n",df2)

    result=pd.merge(df1,df2,left_index=True,right_index=True,suffixes=('_df1','_df2'))
    result['linkage']=-1
    for i in range(40):
        startdate=int(df1.shape[0]/40*i)
        lastdate=int(df1.shape[0]/40*(i+1))
        result_list=[]
        df1_delay=df1[startdate:lastdate]
        df2_delay=df2[startdate:lastdate]
        # print("df1_delay: \n",df1_delay)
        # print("df2_delay: \n",df2_delay)
        for i in range(5):
            corr=df1_delay['Close_normal'].corr(df2_delay['Close_normal'])
            result_list.append(corr)
            df1_delay,df2_delay=delay_df(df1_delay,df2_delay,5)
        max_val,max_inde=find_max_and_index(result_list)
        if max_val<0.7:
            pass
        else:
            if max_inde>0:
                # print(result)
                result.iloc[startdate:lastdate,2]=max_inde
    return result

# ai.training(normalNlabel())

# ai.deep(normalNlabel())