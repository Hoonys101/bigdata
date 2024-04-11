import FinanceDataReader as fdr
import pandas_datareader as web
import datetime as time
import ATO as db
import pandas as pd
import matplotlib.pyplot as plt
import os
from pykrx import stock

# dataFrame, str DB명, stock_code를 받아서, dataFrame에 stock_code,db_name가 추가된 df로 반환
def data_processing(df,db_name,stock_code):
        df.insert(loc=0,column="db_name",value=db_name)
        df.insert(loc=0,column="stock_code",value=stock_code)
        return df
