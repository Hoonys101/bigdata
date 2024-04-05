import sys
import ATO
import scraper as scr
import calculate as cal

input_data=[]
for line in sys.stdin:
    data = line.strip()  
    input_data.append(data)
if input_data[0]=='add_data':
    scr.add_data(list)
elif input_data[0]=='compare_data':
    cal.cal_data(list)
print(input_data[0]+'를 성공적으로 수행하였습니다.')