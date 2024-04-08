import FinanceDataReader as fdr
import ATO as db
import datetime as time
import pandas as pd


strs=['KRX-DESC', 'S&P500','KRX/INDEX/list'] #for stacklisting 'KRX', 'NASDAQ', 'NYSE', 'AMEX' 제외
kr_index=['KS11','KQ11','KS50','KS100','KRX100','KS200']#for DataReader
us_index=['DJI','IXIC','US500','VIX']#for DataReader
global_index=['JP225','STOXX50E','CSI300','HSI','FTSE','DAX','CAC']#for DataReader
current_trade=['USD/KRW','USD/EUR','USD/JPY','USD/RUB']#for DataReader
crypto=['BTC/KRW','ETH/KRW','XRP/KRW','BCH/KRW','EOS/KRW','LTC/KRW','XLM/KRW']#for DataReader

# DB를 list로 받아서, 각 DB의 column명을 list의 list로 반환
def stocklisting(strs=['KRX-DESC']):
    results=[]
    for str in strs:
        result=fdr.StockListing(str)
        if str in ['KRX-DESC', 'KRX/INDEX/list']:
            result.insert(loc=1,column="Nation",value="KR")
        elif str in ['NASDAQ', 'NYSE', 'AMEX', 'S&P500']:
            result.insert(loc=1,column='Nation',value="US")
        result.insert(loc=2, column="db_name", value=str)
        results.append(result)
    transfored_results=pd.concat(results,ignore_index=True)
    return transfored_results

# 외부 DB에서 데이터를 받아서, DB명, Symbol, name, sector, industry이름으로 저장
def db_rename_listing(strs):
    result=[]
    for str in strs:
        if str=='KRX-DESC':
            re=fdr.StockListing(str)[['Code','Market','Sector','Name']].rename(columns={'Market':'db_name','Code':'stock_code'})
            re.insert(loc=1,column="Nation",value="KR")
            re.loc[re['Sector'].isnull(), 'Sector'] = '우선주'
            print(re)
            result.append(re)
        elif str=='S&P500':
            snp500=fdr.StockListing(str)[['Symbol','Sector','Name']]
            snp500.insert(loc=1,column='Nation',value="US")
            snp500.insert(loc=2,column='db_name', value='SnP500')
            snp500.rename(columns={'Symbol':'stock_code'},inplace=True)
            
            print(snp500)
            result.append(snp500)
        elif str=='KRX/INDEX/list':
            index=fdr.SnapDataReader(str)[['Market','Code','Name']]
            index.rename(columns={'Market':'Sector','Code':'stock_code'},inplace=True)
            index.insert(loc=0,column='Nation',value='KR')
            index.insert(loc=1,column='db_name',value='Index')
            result.append(index)
        else:
            rest=fdr.StockListing(str)[['Name','Industry','Symbol']]
            rest.insert(loc=1,column='db_name', value=str)
            rest.rename(columns={'Symbol':'stock_code','Industry':'Sector'}, inplace=True)
            rest.insert(loc=0,column='Nation',value="US")
            result.append(rest)
    result=pd.concat(result,ignore_index=True)
    return result

#print(fdr.DataReader('5049','2020-01-01','2024-04-02').to_string())
#print(stock.get_index_ohlcv("20190101", "20220711", "1008"))
#print(fdr.DataReader('005930','2020-01-01','2024-04-02'))
#print(fdr.SnapDataReader('KRX/INDEX/list'))
#df=df[df['stock_code']!='114840']
#print(db_rename_listing(strs))

#strs의 db에 있는 정보를 "AvailableData"라는 이름으로 저장('문자는 모두 삭제)
def save_data():
    connection=db.connect_to_oracle()
    df=db_rename_listing(strs)
#    db.create_table_from_dataframe(connection,df,'AvailableData')
    df['Sector']=df['Sector'].str.replace("'","")
    df['Name']=df['Name'].str.replace("'","")
    db.insert_data_menu_to_table(connection,df,'AvailableData')
    db.close_connection(connection)

save_data()
# 시작 날짜, 끝 날짜를 8자리 str으로 받고, 검색 대상을 str으로 받아서(한국주식은 6자리 코드) df로 반환

# 8자리 날짜 str을 datetime으로 반환
def str_to_date(str_date='20121212'):
    year=int(str_date[0:4])
    month=int(str_date[4:6])
    day=int(str_date[6:8])
    return time.datetime(year,month,day)

pd.set_option('display.max_columns', None)
'''
columns=[]
for str in strs:
    columns.append(str)
    columns.append(fdr.StockListing(str).columns.tolist())
print(columns)

#df_krx = fdr.StockListing('KRX-DESC')
#print(df_krx.head())
df = fdr.DataReader('IMO', '2015-01-01','2016-01-01')
print(df.head(10))
#plt.plot(df.index,df['Close'])
df2=fdr.DataReader('CBOE', '2015-01-01','2016-01-01')
#plt.plot(df2.index,df2['Close'])
#plt.show()

# Close 컬럼의 최대값과 최소값 계산
max_close = df['Close'].max()
min_close = df['Close'].min()

# Close 값을 -100부터 100까지의 범위로 정규화하여 새로운 열 추가
df['Close_normal'] = ((df['Close'] - min_close) / (max_close - min_close)) * 200 - 100

# 플롯 생성
plt.plot(df.index, df['Close_normal'])

# Close 컬럼의 최대값과 최소값 계산
max_close = df2['Close'].max()
min_close = df2['Close'].min()

# Close 값을 -100부터 100까지의 범위로 정규화하여 새로운 열 추가
df2['Close_normal'] = ((df2['Close'] - min_close) / (max_close - min_close)) * 200 - 100

# 플롯 생성
plt.plot(df2.index, df2['Close_normal'])
correlation=df['Close_normal'].corr(df2['Close_normal'])
plt.title('Correlation between "IMO" and "CBOE" is'+str(correlation))
reverse_correlation=df2['Close_normal'].corr(df['Close_normal'])
print(correlation,reverse_correlation)
#plt.show()
# 폴더가 없으면 생성
save_dir = 'plots'
if not os.path.exists(save_dir):
    os.makedirs(save_dir)

# 그래프를 'plots' 폴더에 그림 파일로 저장

filename='IMO'+'_'+'CBOE'+'_'+'2015-01-01'+'_'+'2016-01-01'+'.png'
plt.savefig(os.path.join(save_dir, filename))

df_krx = fdr.StockListing('KRX-DESC')
print(df_krx.head())

df_amex = fdr.StockListing('AMEX')
print(df_amex.head())
'''

#print(row_price_date())

#print(stockprice())
#stockpri=stockprice()
#connection=db.connect_to_oracle()
#db.create_table_from_dataframe(connection,stockpri,"stock")
#db.insert_data_to_table(connection,stockpri,"stock")
#db.TABLE_NAME="stock"
#db.read_date(connection)

#data_list=stocklisting(strs)

#print(stocklisting(strs))
#print(stocklisting(['KRX-DESC']))

#connection=db.connect_to_oracle()
#db.create_table_from_dataframe(connection,data_list,"data_list")
#db.insert_data_to_table(connection,data_list,"data_list")
#db.TABLE_NAME="data_list"
#db.read_date(connection,start='20120704',last='20220101',TABLE_NAME="data_list")
#db.read_db_code(connection)