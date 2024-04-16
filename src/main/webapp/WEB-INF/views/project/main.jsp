@GetMapping("analysis_page.do") //분석하기
    public String analysis_page(Model model ,HttpSession session,AvailableData availableData){
        String id = (String)session.getAttribute("id");
        List<AvailableData> list = availableDataService.getList(id,availableData);
        model.addAttribute("list" ,list);
        System.out.println("list"+list);
        return "/project/analysis_page";
    }
    @PostMapping("analysis_page.do") //분석하기
    public String analysis_page(@RequestParam("stock_code1") String stock_code1,
                                @RequestParam("stock_code2") String stock_code2,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("end_date") String end_date,
                                HttpSession session, Model model) {
        // Logger 설정
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            logger.info("Analysis start");

            // 세션에서 ID 가져오기
            String id = (String) session.getAttribute("id");
            logger.info("Session ID: {}", id);

            // 날짜 처리
            start_date = start_date.replace("-", "").substring(0, 8);
            end_date = end_date.replace("-", "").substring(0, 8);

            // 서비스 사용 객체 설정
            serviceUsage.setStock_code1(stock_code1);
            serviceUsage.setStock_code2(stock_code2);
            serviceUsage.setStart_date(start_date);
            serviceUsage.setEnd_date(end_date);
            serviceUsage.setId(id);

            logger.info("Service usage object: {}", serviceUsage);

            // Python 스크립트 실행 및 결과 반환
            List<String> result = javaPy.strParameter("cal_data", stock_code1, stock_code2, start_date, end_date);
            logger.info("Result: {}", result);

            // 결과 리스트 길이 조정
            int length = result.size();
            for (int i = 0; i < 10; i++) {
                result.set(i, result.get(length + i - 10));
            }
            logger.info("Adjusted result: {}", result);

            // 분석 보고서 생성
            String report = javaPy.analysisData(result.subList(0, 4));
            logger.info("Analysis report: {}", report);

            // 이미지 파일 경로 설정
            List<String> imagePaths = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                String imagePath = "/img/plots/" + result.get(i);
                imagePaths.add(imagePath);
                logger.info("Image paths: {}", imagePaths);
            }



            // 서비스 사용 데이터 저장
            addData.insertToServiceUsage(serviceUsage);

            // 데이터 리스트 설정
            List<String[]> dataList = new ArrayList<>();
            dataList.add(new String[]{"0주", result.get(0)});
            dataList.add(new String[]{"1주", result.get(1)});
            dataList.add(new String[]{"2주", result.get(2)});
            dataList.add(new String[]{"3주", result.get(3)});
            dataList.add(new String[]{"4주", result.get(4)});

            model.addAttribute("dataList", dataList);
            model.addAttribute("report", report);
            model.addAttribute("plots", imagePaths);

            return "/project/chart";
        } catch (Exception e) {
            logger.error("An error occurred during analysis", e); // 예외 로깅
            // 예외 발생 시 처리할 내용 추가
            return "/project/analysis_page"; // 예외 페이지로 리다이렉트 또는 에러 처리
        }
    }

    //@GetMapping("chart.do") //결과값
    //public String chart(@RequestParam("stock_code1") String stock_code1,
      //                  @RequestParam("stock_code2") String stock_code2,
        //                @RequestParam("start_date") String start_date,
          //              @RequestParam("end_date") String end_date, Model model){
        //model.addAttribute("stock_code1", stock_code1);
        //model.addAttribute("stock_code2", stock_code2);
        //model.addAttribute("start_date", start_date);
        //model.addAttribute("end_date", end_date);
        //model.addAttribute("imagePath", imagePath);
        //  result.size() 12개 2~6 결과값 7~11 그림
        // List<String> subList = result.subList(7, 12);
        //<div>
        //    <h2>Plot</h2>
        //    <img src="img/plots/${stock_code1}_${stock_code2}_${start_date}_${end_date}.png" alt="Plot">
        //</div>
      //  return  "/project/chart";
    //}
        // ArchivedData  ['add_data', 'KONEX', '317240'] db name , 스톡 코드 기업추가

        // 검색 국가 DB 회사명 종목 dto (nation, db_name, sector, name)

        // 파라미터 id, stockcode
        // addition id 값조회
        // ArchivedData  stockcode 조회
        // ?? 조인결과 조회 후 리턴

        // 추가 테이블의 id가 갖고있는 스톡코드로 나라 db 종목 회사를 쿼리한다

        // 쿼리문 SELECT nation, db_name, sector, name
        //FROM AvailableData
        //INNER JOIN addition ON AvailableData.stock_code = addition.stock_code
        //WHERE addition.id = '1' AND AvailableData.stock_code = '317240'
        //GROUP BY nation, db_name, name, sector;
        //기업선택 1 , 기업선택 2 , 시작날짜 , 종료날짜

        //javaPy.strParameter("cal_dat",first_stock_code,second_stock_code,startdate,lastdate);
        //




}
package team.backend.service;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JavaPython implements JavaPythonInter {
    //public static void main(String[] args){
        //JavaPython java = new JavaPython();
//      java.save_data();
        //java.strParameter("add_data","Index","1008");
        //java.strParameter("add_data","SnP500","IBM");
        //java.strParameter("cal_data","1008","IBM","20130101","20140101");
    //}
    public static void main(String[] args){
        JavaPython java = new JavaPython();

//        java.strParameter("add_data","Index","1008");
//        java.strParameter("add_data","SnP500","IBM");
        java.strParameter("cal_data","1008","IBM","20130101","20140101");
    }
    @Override
    public List<String> strParameter(String... args){
        String resultString=args[0];
        for(int i=1;i<args.length;i++){
            resultString=resultString+"\n"+args[i];
        }
        if(args[0].equals("add_data")&&args.length==3){
            pln("add_data에 들어옴:"+resultString);
            List<String> result=save_data(resultString);
            for(String str : result) {
                pln(str);
            }
            return null;
        }else if(args[0].equals("cal_data")&&args.length==5){
            List<String> result=cal_data(resultString);
            for(String str : result) {
                pln(str);
            }
            return result;
        }else if (args[0].equals("diff_cal_data")&&args.length==9){
            return cal_data(resultString);
        }else{
            return null;
        }
    }
    void pln(String str){
        System.out.println(str);
    }
    List<String> save_data(String data){

        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py");
        Process pp=null;
        try{
            pp = pb.start();
            pln("save_data 실행");
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
//            data = "cal_data\n1008\nIBM\n20130101\n20140101";
            writer.println(data);
            writer.flush();
            writer.close();
            outputStream.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
        // 파이썬 스크립트의 출력 읽어오기
        List<String> result= new ArrayList<>();
        String line;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream(),"cp949"));
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }

            // 프로세스 종료
            reader.close();
            return result;
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            return null;
        }
    }

    List<String> cal_data(String data){
        pln("cal_data 실행");
        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py");
        Process pp=null;
        try{
            pp = pb.start();
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
//            data = "cal_data\n1008\nIBM\n20130101\n20140101";
            writer.println(data);
            writer.flush();
            writer.close();
            outputStream.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            ie.printStackTrace();
        }
        // 파이썬 스크립트의 출력 읽어오기
        String line;
        List<String> result = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream(),"cp949"));
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }

            // 프로세스 종료
            reader.close();
            pp.destroy();
            return result;
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            return null;
        }
    }
    @Override
    public String analysisData(List<String> corre){
        String result="";
        List<Double> data= new ArrayList<>();
        double max=-2;
        int maxIndex = -1;
        for (int i =0 ;i<corre.size(); i++){
            double current=Double.parseDouble(corre.get(i));
            data.add(current);
            if(current>max){
                max=current;
                maxIndex=i;
            }
        }
        if (Collections.max(data)<0.5){
            result="큰 관련이 없습니다.(correlationMax<0.5)\n";
        }else if(maxIndex==0){
            result=result+"두 데이터는 동시에 움직입니다.\n";
        }else if(maxIndex>0&&maxIndex<3){
            result=result+"두 데이터는 "+maxIndex+"주의 간격을 두고 전파되는 관계에 있습니다.\n";
        }else{
            result=result+"두 데이터는 5주 이상의 간격을 두고 전파되는 관계에 있습니다.\n";
        }
        return result;
    }
}
import ATO as db
import pandas as pd
import matplotlib.pyplot as plt
import os



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
def saveplot(df,df2,filename):
    plt.plot(df.index, df['Close_normal'])
    plt.plot(df2.index, df2['Close_normal'])
    correlation=df['Close_normal'].corr(df2['Close_normal'])
    plt.title('Correlation is'+str(correlation))
    # 폴더가 없으면 생성
    save_dir = './src/main/resources/static/img/plots'
    if not os.path.exists(save_dir):
#        print("폴더가 없어서 새로 만듭니다.")
        os.makedirs(save_dir)
    # 그래프를 'plots' 폴더에 그림 파일로 저장
    plt.savefig(os.path.join(save_dir, filename))
#    print("파일이 저장된 경로:", os.path.abspath(save_dir))
    plt.clf()
    return correlation

#df2개와 int를 받아서 int만큼 뒤로 당긴 df로 변환하고, 두 df의 격차 제거한 df 반환하는 함수
def delay_df(df1,df2,days):

    df1=df1.dropna()
    df2=df2.shift(-days)
    df2=df2.dropna()
    common_dates=df1.index.intersection(df2.index)
    df1=df1.loc[common_dates]
    df2=df2.loc[common_dates]
    return df1,df2

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
    print(first_com,second_com,startdate,lastdate)
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
import sys
import scraper as scr
import calculate as cal
#print("표준 출력의 인코딩:", sys.stdout.encoding)
#print("표준 입력의 인코딩:", sys.stdin.encoding)
input_data=[]
try:
    for line in sys.stdin:
        data = line.strip()
        input_data.append(data)
#    print("파라미터를 모두 받았습니다.")
#    print(input_data)
    if input_data[0]=='add_data':#input_data는 0,1,2 값만 가질 것
#        print('input_data로 들어왔습니다.')
        scr.add_data(input_data)
        print(input_data[0]+'를 성공적으로 수행하였습니다.')
    elif input_data[0]=='cal_data':#input_data는 0,1,2,3,4 값을 가질 것
#        print('cal_data로 들어왔습니다.')
        print(input_data)
        result=cal.cal_data(input_data)
        for str_value in result:
            print(str_value)
#        print(input_data[0]+'를 성공적으로 수행하였습니다.')
    elif input_data[0]=='diff_cal_data':#input_data는 0,1,2,3,4,5,6 값을 가질 것
#        print('diff_cal_data로 들어왔습니다.')
        print(input_data)
        result=cal.diff_cal_data(input_data)
        for str_value in result:
            print(str_value)
#        print(input_data[0]+'를 성공적으로 수행하였습니다.')

except Exception as e:
    print("예외가 발생했습니다:", e)

<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>분석 결과 리포트</title>
    <style>
        .inline-img {
            display: none; /* 이미지는 숨김 처리 */
            margin-right: 10px;
            width: 200px;
        }
        #loading { /* 로딩 화면 스타일 */
            display: none;
            position: fixed;
            z-index: 9999;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
    </style>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
<div id="loading">Loading...</div> <!-- 로딩 화면 -->
<div>${report}</div>
<div id="images"> <!-- 이미지를 담을 div -->
    <c:if test="${not empty plots}">
        <c:forEach items="${plots}" var="plots" varStatus="loop">
             <img src="${plots}" alt="그래프" class="inline-img">
        </c:forEach>
    </c:if>
</div>
<!-- 그래프가 표시될 div 요소 -->
<div id="chart_div" style="width: 900px; height: 500px;"></div>

<script type="text/javascript">
    var dataArray = [
        ['Index', 'Value'],
        <c:forEach items="${dataList}" var="data" varStatus="loop">
            ['${data[0]}', ${data[1]}]<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ];

    // Google Charts API 초기화
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    // 차트를 그리는 함수
    function drawChart() {
        // 데이터 생성
        var data = google.visualization.arrayToDataTable(dataArray);

        // 옵션 설정
        var options = {
            title: '분석결과',
            curveType: 'function',
            legend: { position: 'bottom' }
        };

        // 차트 생성 및 그리기
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);

        // 이미지가 로드되었을 때 처리
        var images = document.getElementsByClassName('inline-img');
        for (var i = 0; i < images.length; i++) {
            images[i].style.display = 'inline-block'; // 이미지 보이기
        }
        document.getElementById('loading').style.display = 'none'; // 로딩 화면 숨기기
    }
</script>
</body>
</html>
더 필요한 파일명있으면 알려줘 일단 이렇게줄게