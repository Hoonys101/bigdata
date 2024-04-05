import sys
import scraper as scr
import calculate as cal

#input_data=[]
#for line in sys.stdin:
#    data = line.strip()
#    input_data.append(data)
# if input_data[0]=='add_data':#input_data는 0,1,2 값만 가질 것
#     scr.add_data(list)
# elif input_data[0]=='cal_data':#input_data는 0,1,2,3,4,5,6 값을 가질 것
#     print(cal.cal_data(list))
# print(input_data[0]+'를 성공적으로 수행하였습니다.')

#list=['cal_data','1008','Index','IBM','SnP500','20120101','20130202']
#print(cal.cal_data(list))


input_data=['add_data','1008','Index']
scr.add_data(input_data)
input_data=['add_data','IBM','SnP500']
scr.add_data(input_data)
input_data=['cal_data','1008','Index','IBM','SnP500','20120101','20130202']
print(cal.cal_data(input_data))