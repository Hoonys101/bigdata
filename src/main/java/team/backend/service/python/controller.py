import sys
import scraper as scr
import calculate as cal
#print("표준 출력의 인코딩:", sys.stdout.encoding)
#print("표준 입력의 인코딩:", sys.stdin.encoding)
while True:
    input_data=[]    
    try:
        for line in sys.stdin:
            data = line.strip()
            if data=="EOF":
                # print("입력이 끝났습니다.",flush=True)
                break
            input_data.append(data)
            # print(data,flush=True)
        # print("파라미터를 모두 받았습니다.",flush=True)
        # print(input_data,flush=True)
        if input_data[0]=='add_data':#input_data는 0,1,2 값만 가질 것
            # print('input_data로 들어왔습니다.',flush=True)
            scr.add_data(input_data)
            # print(input_data[0]+'를 성공적으로 수행하였습니다.',flush=True)
            print("EOF",flush=True)
        elif input_data[0]=='cal_data':#input_data는 0,1,2,3,4 값을 가질 것
            # print('cal_data로 들어왔습니다.',flush=True)
            # print(input_data,flush=True)
            result=cal.cal_data(input_data)
            for str_value in result:
                print(str_value,flush=True)
            # print(input_data[0]+'를 성공적으로 수행하였습니다.',flush=True)
            print("EOF",flush=True)
        elif input_data[0]=='diff_cal_data':#input_data는 0,1,2,3,4,5,6 값을 가질 것
        #        print('diff_cal_data로 들어왔습니다.')
            # print(input_data,flush=True)
            result=cal.diff_cal_data(input_data)
            for str_value in result:
                print(str_value,flush=True)
            # print(input_data[0]+'를 성공적으로 수행하였습니다.',flush=True)
            print("EOF",flush=True)
        elif input_data[0]=='find_period':#input_data는 0,1,2 값을 가질 것
#            print('find_period에 들어왔습니다.',flush=True)
#            print(input_data,flush=True)
            cal.total_analy(input_data)
            print("EOF",flush=True)
    except Exception as e:
        print("예외가 발생했습니다:",e,flush=True)
        print("EOF",flush=True)