import DAO as db
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
import numpy as np
import os
import ai
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures
import datetime
import scraper as scr
import concurrent.futures

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
    # print(type(df))
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
def saveplot(df,df2,filename,column:str='Close_normal')-> float:
    plt.plot(df.index, df[column])
    plt.plot(df2.index, df2[column])
    correlation=df[column].corr(df2[column])
    correlation=f"{correlation:.4f}"
    plt.title('Correlation is '+ str(correlation))
    plt.xlabel('Date')
    plt.gca().xaxis.set_major_formatter(mdates.DateFormatter('%m-%d'))
    plt.grid()
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
def delay_df(df1: pd.DataFrame,df2: pd.DataFrame,days=5)-> tuple[pd.DataFrame,pd.DataFrame]:

    df1=df1.dropna()
    df2=df2.shift(-days)
    df2=df2.dropna()
    common_dates=df1.index.intersection(df2.index)
    df1=df1.loc[common_dates]
    df2=df2.loc[common_dates]
    return df1,df2

# df를 찾아서 추세선을 만들고 변곡점 특정
def make_trend_and_find_inflect(df:pd.DataFrame,degree:int=2,value:str='Close_normal')->tuple[pd.DataFrame,list[int]]:

    # 다항 회귀 모델 학습
    # X = np.array(df.index).reshape(-1, 1)  # 날짜를 입력값으로 사용
    # X = np.array([int(date.strftime('%Y%m%d')) for date in df.index]).reshape(-1, 1)  # 날짜를 정수로 변환하여 입력값으로 사용
    X = np.arange(1, len(df) + 1).reshape(-1, 1)  # 날짜를 1부터 시작하여 순차적인 정수로 변환하여 입력값으로 사용
    # print(X)
    y = df[value].values.reshape(-1, 1)
    # print(y)
    poly_features = PolynomialFeatures(degree)  # 5차 다항식 사용 (조절 가능)
    X_poly = poly_features.fit_transform(X)

    model = LinearRegression()
    model.fit(X_poly, y)

    # 추세선 예측
    y_pred = model.predict(X_poly)
    
    # # 모델의 다항식 출력
    # coefficients = model.coef_[0]
    # polynomial = f"{coefficients[0]} + "

    # for i in range(1, len(coefficients)):
    #     polynomial += f"{coefficients[i]} * X^{i} + "

    # print("다항식:", polynomial)

    # 추세선 그래프 그리기
    plt.figure(figsize=(10, 6))
    plt.plot(df.index, df[value], label=value)
    plt.plot(df.index, y_pred, label='TREND', linestyle='--')
    plt.xlabel('DATE')
    plt.ylabel(value)
    plt.title('CLOSE and trend')
    plt.legend()

    # # 변곡점 찾기
    # coefficients = model.coef_[0]
    # derivative = np.polyder(coefficients)
    # roots = np.roots(derivative)
    # print('roots',roots)

    # 극값 찾기
    coefficients = model.coef_[0]
    derivative = np.polyder(coefficients)
    roots = np.roots(derivative)
    # print('roots',roots)
    extrema = []
    for root in roots:
        if np.isreal(root):
            x = root.real  # 근의 실수부
            if x >= 0 and x <= len(df):  # 주어진 데이터 범위 내에서만 검사
                # y = np.polyval(coefficients, x)  # 다항식 함수의 값을 계산하여 극점(극대값 또는 극소값) 확인
                extrema.append(x)
    
    # 변곡점을 그래프에 표시
    # for root in roots:
    for root in extrema:
        if np.isreal(root):
            index = int(root)
            # print(index)
            # date_at_index=df.index[index]
            plt.scatter(df.index[index], df.iloc[index][value], color='red', marker='o', label='변곡점')

    # plt.show()

#stock code와 날짜2개를 받아서 df생성
def make_df_with_dates(first_com:str='1008',startdate:str='20211201',lastdate:str='20220101')->pd.DataFrame:
    connection=db.connect_to_oracle()
    df=db.read_code_date(connection,first_com,startdate,lastdate)
    connection.close()
    return df

# df=make_df_with_dates('1008','20130101','20130401')
# df=normal(df)
# make_trend_and_find_inflect(df,4)
# make_trend_and_find_inflect(df,7)
# make_trend_and_find_inflect(df,10)
# plt.show()

# 3-1개의 파라미터를 받아서 2개의 str 반환
def ai_anal(list,decimal:str=None,critical_profit=1)->list[str]:
    #파라미터 설정
    first_com=list[1]
    second_com=list[2]
    # print(first_com,second_com)
    df=normalNlabel(first_com,second_com,decimal)
    result=ai.training(df)
    if result[1]==-1:
        result[1]="아쉽게도 현재 주가 전파관계는 없는 것으로 보이네요."
        result.append("주가를 예측할 수 없습니다.")
    elif result[1]==0:
        result[1]="현재 주가는 동시에 움직이고 있습니다."
        result.append("선행지표가 없으므로 주가를 예측할 수 없습니다.")
    elif result[1]>0:
        #생각 정리
        #추세선이 변곡점이 1 이하가 나올 때까지 가공->포기
        
        #result주일 전 선행지표가 상승중, 현재 사이에 변곡점 존재(n개) 최대치가 나오는 날을 추천하고, 변곡점이 ->포기
        #result주일 전 선행지표가 하락중, 현재 사이에 변곡점 존재 변곡점의 높이가 result주일전 선행지표의 높이보다 작을 것->포기
        #result주일 전 선행지표 가격이, 선행지표의 현재 가격 사이의 가격의 max 값보다 작고, 그 차가 10보다 크다면, 사세요. max일 때 파세요.
        #result주일 전 선행지표 가격이, 선행지표의 현재 가격 사이의 가격의 min 값보다 크고, 그 차가 10보다 크다면, 파세요. min일 때 파세요.
        # lastdate_for_expect=datetime.datetime(2022,1,1).strftime('%Y%m%d')
        # startdate_for_expect=(datetime.datetime(2022,1,1)-datetime.timedelta(days=result[1])).strftime('%Y%m%d')
        # print(first_com)
        # print('start:',str(startdate_for_expect))
        # print('last:',str(lastdate_for_expect))
        df=make_df_with_dates(second_com)
        df = df.iloc[-1*int(result[1])*5:, :]
        # print(df,first_com,(datetime.datetime(2022,1,1)+datetime.timedelta(days=result[1]*7)).strftime('%Y%m%d'),datetime.datetime(2022,1,1).strftime('%Y%m%d'))
        startvalue=df['CLOSE'].iloc[0]
        
        max_value=df['CLOSE'].max()
        
        
        # print(max_position,startvalue,max_interval)
        min_value=df['CLOSE'].min()
        # startdate=df.first_valid_index()
        # max_position=df['CLOSE'].idxmax()
        # max_interval=max_position-startdate
        # min_position=df['CLOSE'].idxmin()
        # min_interval=min_position-startdate
        if startvalue<max_value:
            max_workingdays_interval=df.reset_index()['CLOSE'].idxmax()
            estimated_profit_ratio=(max_value-startvalue)/startvalue*100
        elif startvalue>min_value:
            min_workingdays_interval=df.reset_index()['CLOSE'].idxmin()
            estimated_profit_ratio=(startvalue-min_value)/startvalue*100
        
        result[1]="현재 주가전파관계가 존재합니다. 확인해보세요. 예측치:"+str(result[1])+"주 후 전파"
        result.append('기준주식: '+first_com+'비교대상주식: '+second_com)
        if startvalue<max_value and estimated_profit_ratio>critical_profit:
            result.append("지금은 살 때입니다."+str(max_workingdays_interval)+'업무일 후에 파세요')
            result.append('예상수익률은 '+str(estimated_profit_ratio)+'%입니다.')
            result.append('수익은 주당 '+str(estimate_profit(first_com,max_workingdays_interval)*100)+'%')
        elif startvalue>min_value and estimated_profit_ratio>critical_profit:
            result.append("지금은 팔 때입니다."+str(min_workingdays_interval)+'업무일 후에 사세요')
            result.append('예상수익률은 '+str(estimated_profit_ratio)+'%입니다.')
            result.append('수익은 주당 '+str(estimate_profit(first_com,min_workingdays_interval,False)*100)+'%')
        else:
            result.append("향후 변동폭이 크지 않습니다. 판단을 보류합니다."+ '제안을 해도 예상수익률은 '+str(estimated_profit_ratio)+'%로 미진합니다.')
        #지금은 살 때입니다. ~~업무일,~~업무일,~~업무일 후에 파세요.
        #지금은 팔 때입니다. ~~업무일 후에 사세요.
    return result

def estimate_profit(stock_code:str='058430',i:int=5,b: bool=True)->float:
    if len(stock_code)==4 and stock_code.isdigit():
        df=scr.stock_data2('Index',stock_code,startdate='20220101',lastdate='20220220',default='Close')
    else:
        df=scr.stock_data2('sdf',stock_code,startdate='20220101',lastdate='20220220',default='Close')
    # print('미래의 data \n',df)
    # print("DataFrame의 길이:", len(df))
    # print('i의 값',i)
    # print(df.columns)
    initvalue=df['Close'].iloc[0]
    lastvalue=df['Close'].iloc[i]
    if b:
        return (lastvalue-initvalue)/initvalue
    else:
        return -(lastvalue-initvalue)/initvalue
#7-1개의 파라미터를 받아서 파일 5개와 correlation 5개 반환
def diff_cal_data(list,days=5)->list[str]:
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
def cal_data(list:list[str],days=5)->list[str]:
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
    # print(startdate,lastdate)
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

# 리스트를 받아서 극대값 위치와 극대값 산출
def find_max_and_index(lst:list=[1,2,3,2,1])->tuple[int,int]:
    if not lst:  # 리스트가 비어있는 경우
        return None, None
    max_value = max(lst)
    max_index = lst.index(max_value)
    return max_value, max_index
# 리스트를 받아서 극소값 위치와 극소값 산출
def find_min_and_index(lst:list=[3,2,1,2,3])->tuple[int,int]:
    if not lst:  # 리스트가 비어있는 경우
        return None, None
    min_value = min(lst)
    min_index = lst.index(min_value)
    return min_value, min_index

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


# 두 pf를 입력받아서 같은 날이 존재하는 항목만 남긴 두 pf로 변환
def common_date(df1:pd.DataFrame,df2:pd.DataFrame)->tuple[pd.DataFrame,pd.DataFrame]:
    common_dates=df1.index.intersection(df2.index)
    df1=df1.loc[common_dates]
    df2=df2.loc[common_dates]
    return df1,df2
    
# 1년을 4개월씩 분석
def yearly(df1:pd.Series,df2:pd.Series,diff:pd.Series=None,normal_diff=None,div:int=4,critic:float=0.7)->list[str]:
    result=[]
    for i in range(div):
        startdate=int(len(df1)/div*i)
        lastdate=int(len(df1)/div*(i+1))
        if normal_diff!=None:
            df1_normal,df2_normal=normal_diff(df1[startdate:lastdate],df2[startdate:lastdate],diff[startdate:lastdate])
            correlation = df1_normal.corr(df2_normal)
            if correlation>critic:
    #            print(df1.index[startdate].date(),"와 " ,df1.index[lastdate-1].date(), " 사이의 분기에 양의 상관관계")
    #            print("correlation: ",correlation)            
                result.append(delay_save(df1_normal,df2_normal,str(startdate)+'_'+str(lastdate)))
        else:
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
    return result

#list 0,1,2를 받아서 total_anal 실행
def total_analy(lst:list)->list[str]:
    stock_code1=lst[1]
    stock_code2=lst[2]
    return total_anal(stock_code1,stock_code2)
# 10 년 전체를 년별 분석
def total_anal(stock_code1:str='1008',stock_code2:str='IBM',yearly=yearly,default:str='CLOSE')->list[str]:
    result=[]
    df1=make_df(stock_code1)[default]
    df2=make_df(stock_code2)[default]
    df1,df2=common_date(df1,df2)
    for i in range(10):
        startdate=int(df1.shape[0]/10*i)
        lastdate=int(df1.shape[0]/10*(i+1))
        temp_result=yearly(df1[startdate:lastdate],df2[startdate:lastdate])
        if len(temp_result)>0:
            result.extend(temp_result)
        # correlation = df1[startdate:lastdate].corr(df2[startdate:lastdate])
        # if correlation>0.5:
        #     # print(df1.index[startdate].date(),"와 " ,df1.index[lastdate].date(), " 사이의 기간에 연간 양의 상관관계가 있습니다.")
        #     # print("correlation: ",correlation)            
        #     result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))
            
        # elif correlation<-0.5:
        #     # print(df1.index[startdate].date(),"와 ",df1.index[lastdate].date()," 사이의 기간에 연간 음의 상관관계가 있습니다.")
        #     # print("correlation: ",correlation)
        #     result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))
        # else:
        #     # print(df1.index[startdate-1].date(),"와 ",df1.index[lastdate-1].date()," 사이의 기간에 상관관계가 적습니다.")
        #     # print("correlation: ",correlation)
        #     result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))

    # df1,df2=delay_df(df1,df2)
    return result
def diff_find_period(lst:list)->list[str]:
    stock_code1=lst[1]
    diff_code1=lst[2]
    stock_code2=lst[3]
    # diff_code2=lst[4]
    return diff_find_period_work(stock_code1,diff_code1,stock_code2)
def normal_series(df:pd.Series)->pd.Series:
    max_close=df.max()
    min_close=df.min()
    df=((df-min_close)/(max_close-min_close))*200-100
    return df

def normal_diff(df1:pd.Series,df2:pd.Series,diff:pd.Series)-> tuple[pd.Series,pd.Series]:
    df1=normal_series(df1)
    df2=normal_series(df2)
    diff=normal_series(diff)
    df1=df1-diff
    df2=df2-diff
    return df1,df2
def diff_find_period_work(stock_code1:str='1008',diff_code:str='1001',stock_code2:str='IBM',yearly=yearly,default:str='CLOSE')->list[str]:
    
    result=[]
    df1=make_df(stock_code1)[default]
    df2=make_df(stock_code2)[default]
    diff=make_df(diff_code)[default]
    df1,diff=common_date(df1,diff)
    df2,diff=common_date(df2,diff)
    df1,diff=common_date(df1,diff)
    # print(len(df1))
    
    # 분기별로 normalize해서 차를 계산해야 맞을 듯.
    for i in range(10):
        startdate=int(df1.shape[0]/10*i)
        lastdate=int(df1.shape[0]/10*(i+1))
        temp_result=yearly(df1[startdate:lastdate],df2[startdate:lastdate],diff[startdate:lastdate],normal_diff)
        if len(temp_result)>0:
            result.extend(temp_result)
        # correlation = df1[startdate:lastdate].corr(df2[startdate:lastdate])
        # if correlation>0.5:
        #     # print(df1.index[startdate].date(),"와 " ,df1.index[lastdate].date(), " 사이의 기간에 연간 양의 상관관계가 있습니다.")
        #     # print("correlation: ",correlation)            
        #     result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))
            
        # elif correlation<-0.5:
        #     # print(df1.index[startdate].date(),"와 ",df1.index[lastdate].date()," 사이의 기간에 연간 음의 상관관계가 있습니다.")
        #     # print("correlation: ",correlation)
        #     result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))
        # else:
        #     # print(df1.index[startdate-1].date(),"와 ",df1.index[lastdate-1].date()," 사이의 기간에 상관관계가 적습니다.")
        #     # print("correlation: ",correlation)
        #     result.append(yearly(df1[startdate:lastdate],df2[startdate:lastdate]))

    # df1,df2=delay_df(df1,df2)
    return result

# print(diff_find_period_work('1152','1153','1034'))
# print(total_anal('1152','1153'))
# df1=make_diff('1008')
# df2=make_diff('IBM')
# for i in range(5):
#     correlation = df1.loc['2012-01-01':'2013-02-02','Diff'].corr(df2.loc['2012-01-01':'2013-02-02','Diff'])
#     print(correlation)
#     df1,df2=delay_df(df1,df2)
# lst=['find_period','023440','1153']
# total_analy(lst)

# 두개의 주가 코드를 입력받아서, 하나의 df로 변환
def normalNlabel(stock_code1:str='IBM',stock_code2:str='1008',decimal:str=None,default='CLOSE')->pd.DataFrame:
    df1=make_df(stock_code1)[[default]]
    df2=make_df(stock_code2)[[default]]
    
    # print("df1: \n",df1)
    # print("df2: \n",df2)
    df1,df2=common_date(df1,df2)
    if decimal!=None:
        df3=make_df(decimal)[[default]]
        df3=normal(df3)
        df1=df1-df3
        df2=df2-df3
    else:
        
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
            if max_inde>=0:
                # print(result)
                result.iloc[startdate:lastdate,2]=max_inde
    return result

# ai.training(normalNlabel())


# ai.deep(normalNlabel())pip

# result=diff_cal_data(['diff_cal_data', '1152', '1008', '1153', '1008', '20130101', '20130501'])
# print(result)
# result=total_analy(['find_period', '1008', 'IBM'])
# print(result)
# result=ai_anal(['tree_data', '1008', 'IBM'])
# print(result)

def find_usable(lst:list[str]=[
    '058430',
    '1152',
    '1160',
    '1153',
    '026960',
    'IBM',
    '1154',
    '1020',
    '012330',
    '1011',
    '058650',
    '1159',
    '004020',
    '1008',
    '004100',
    '1034',
    '063160',
    '1155'],decimal:str='1001'):
    param=[]
    final_result=[]
    for stock_code1 in lst:
        for stock_code2 in lst:
            # print(stock_code1,'과',stock_code2)
            result=ai_anal(['asdf',stock_code1,stock_code2],decimal)
            # print(result)
            
            if result[1][0]=='아':
                continue
            else:
                # print(result)
                if result[2][0]!='기' or  (result[2][0]=='기' and result[3][0]=='향'):
                    continue
                final_result.append(result[2])
                final_result.append(result[0])
                final_result.append(result[-2])
                final_result.append(result[-1])
                print(result[2])
                print(result[0])
                print(result[-2])
                print(result[-1])
    return final_result
    #         param.append(['asdf',stock_code1,stock_code2])
    # result=multithread_results(param)
    # return result
    
def multithread_results(lst:list[str,str])->list[str]:
        #쓰레드 풀 생성
    max_thread = 3
    results=[]
    with concurrent.futures.ThreadPoolExecutor(max_workers=max_thread) as executer:
        # print('멀티쓰레드 실행')
        # 작업 실행 및 결과 수집
        futures = [executer.submit(ai_anal,params) for params in lst]
        # print('결과 수집')
        # 결과 처리
        results.add([future.result() for future in concurrent.futures.as_completed(futures)])
        return results

# print(make_df_with_dates())
# print(ai_anal(['1111','1160','1152']))

# print(ai_anal(['111','1001','1004']))
# print(diff_find_period(['111','1004','1002','1001']))
# print(total_analy(['111','1004','1002']))

# fi_result=find_usable()
# print('최종 결과는 ',fi_result)
# for strs in fi_result:
#     print(strs)