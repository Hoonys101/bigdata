import sys
import scraper as scr
import calculate as cal
import threading
#print("표준 출력의 인코딩:", sys.stdout.encoding)
#print("표준 입력의 인코딩:", sys.stdin.encoding)

# 출력은 병목 처리
def sending_result(thread_name:str,lst:list):
    lock=threading.Lock()
    with lock:
        print(thread_name,flush=True)
        for str in lst:
            print(str,flush=True)
        print("EOF",flush=True)
        
def run_service(thread_name:str,input_data:list):
    # print(data,flush=True)
    # print("파라미터를 모두 받았습니다.",flush=True)
    # print(input_data,flush=True)
    if input_data[0]=='add_data':#input_data는 0,1,2 값만 가질 것
        # print('input_data로 들어왔습니다.',flush=True)
        result=scr.add_data(input_data)
        sending_result(thread_name, result)
        # print(input_data[0]+'를 성공적으로 수행하였습니다.',flush=True)
#            print("EOF",flush=True) 통합처리
    elif input_data[0]=='cal_data':#input_data는 0,1,2,3,4 값을 가질 것
        # print('cal_data로 들어왔습니다.',flush=True)
        # print(input_data,flush=True)
        result=cal.cal_data(input_data)
        # for str_value in result:
        #     print(str_value,flush=True)
        sending_result(thread_name, result)
        # print(input_data[0]+'를 성공적으로 수행하였습니다.',flush=True)
        # print("EOF",flush=True)
    elif input_data[0]=='diff_cal_data':#input_data는 0,1,2,3,4,5,6 값을 가질 것
        # print('diff_cal_data로 들어왔습니다.')
        # print("input_data=",input_data,flush=True)
        # print("계산을 시작합니다.",flush=True)
        result=cal.diff_cal_data(input_data)
        # print("결과는 ",name, result,flush=True)
        sending_result(thread_name, result)
        # for str_value in result:
            # print(str_value,flush=True)
        # print(input_data[0]+'를 성공적으로 수행하였습니다.',flush=True)
        # print("EOF",flush=True)
    elif input_data[0]=='find_period':#input_data는 0,1,2 값을 가질 것
        # print('find_period에 들어왔습니다.',flush=True)
        # print(input_data,flush=True)
        result=cal.total_analy(input_data)
        sending_result(thread_name, result)
        # print("EOF",flush=True)
    elif input_data[0]=='tree_data':
        result=cal.ai_anal(input_data)
        sending_result(thread_name, result)
        # print("EOF",flush=True)
    elif input_data[0]==("diff_find_period"):
        result=cal.diff_find_period(input_data)
        sending_result(thread_name,result)

def main():
    while True:
        input_data=[]
        thread_name=None
        try:
            for line in sys.stdin:
                data = line.strip()
                if data=="EOF":
                    # print("입력이 끝났습니다.",flush=True)
                    break
                if thread_name==None:
                    thread_name=data
                    continue
                # print(line)
                input_data.append(data)
            threading.Thread(target=run_service,args=(thread_name,input_data)).start()
                
        except Exception as e:
            print("예외가 발생했습니다:",e,flush=True)
            print("EOF",flush=True)
main()
# print(scr.add_data(['add','Index','1152']))
# scr.add_data(['add','asdf','030520'])
# scr.add_data(['add','asdf','054920'])
# scr.add_data(['add','asdf','047080'])
# print(cal.diff_cal_data(['diff_call_data','1152','002020','1011','002020','20190107','20191212']))
# print(cal.diff_find_period(['diff_find_peiod','1011','1001','1152','1001']))