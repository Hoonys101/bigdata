import sys
import scraper as scr
import calculate as cal

input_data=[]
try:
# #    input_data=['cal_data','1008','Index','IBM','SnP500','20130101','20140101']
    for line in sys.stdin:
        data = line.strip()
        input_data.append(data)
    print("파이썬이 실행되었습니다.")
    # if input_data[0]=='add_data':#input_data는 0,1,2 값만 가질 것
    #     print('input_data로 들어왔습니다.')
    #     scr.add_data(input_data)
    #     print(input_data[0]+'를 성공적으로 수행하였습니다.')
    # elif input_data[0]=='cal_data':#input_data는 0,1,2,3,4,5,6 값을 가질 것
    #     print('cal_data로 들어왔습니다.')
    #     print(cal.cal_data(input_data))
    #     print(input_data[0]+'를 성공적으로 수행하였습니다.')
    # elif input_data[0]=='diff_cal_data':#input_data는 0,1,2,3,4,5,6,7,8,9 값을 가질 것
    #     print('diff_cal_data로 들어왔습니다.')
    #     print(cal.diff_cal_data(input_data))
    #     print(input_data[0]+'를 성공적으로 수행하였습니다.')
    print(input_data)
    print("파이썬이 정상적으로 종료됩니다.")
except Exception as e:
    print("예외가 발생했습니다:", e)
print("print")
#list=['cal_data','1008','Index','IBM','SnP500','20120101','20130202']
#print(cal.cal_data(list))

# input_data=['add_data','1008','Index']
# scr.add_data(input_data)
# input_data=['add_data','IBM','SnP500']
# scr.add_data(input_data)
# input_data=['cal_data','1008','Index','IBM','SnP500','20130101','20140101']
# print(cal.cal_data(input_data))

