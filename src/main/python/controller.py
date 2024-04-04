import sys
import ATO
import scraper as scr

input_data=[]
for line in sys.stdin:
    data = line.strip()  
    input_data.append(data)
if input_data[0]=='add_data':
#    scr.add_data(list)
    scr.cal_corr()
print(input_data[2]+'를 성공적으로 추가하였습니다.')
