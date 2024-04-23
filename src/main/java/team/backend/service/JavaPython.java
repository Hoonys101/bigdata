package team.backend.service;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class JavaPython implements JavaPythonInter {

    @Getter
    private static BufferedReader br;

    @Getter
    private static PrintWriter pw;
    private JavaPython(){
        try{
            Process pythonProcess = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py").start();
            br = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream(),"cp949"));
            pw= new PrintWriter(pythonProcess.getOutputStream());
        }catch(IOException ie){
            System.out.println("생성중 에러 발생 ie: "+ie);
        }
    }

    public static void main(String[] args){

        JavaPython java = new JavaPython();
        System.out.println("find_period");


        //List<String> results=java.strParameter("find_period","054920","030520");

//        List<String> results=java.strParameter("find_period","023440","1153");
        List<String> results=java.strParameter("diff_cal_data","041020","1001","025750","1001","20130101","20130501");

        System.out.println("result printing");
        for(String result:results){
            System.out.println("results"+result);
        }
    }

    @Override
    public synchronized List<String> strParameter(String... args){
        if (args[0].equals("diff_cal_data")&&(args.length==7)){
            String resultString=args[0];
            pln("strParam 첫번째 인자\n"+args[0]);
            resultString=resultString+"\n"+args[1]+"\n"+args[2]+"\n"+args[3]+"\n"+args[4]+"\n"+args[5]+"\n"+args[6];
            resultString=resultString+"\nEOF";
            System.out.println("resultString"+resultString);
            System.out.println("2"+args[1]+"\n"+args[3]+"\n"+args[2]+"\n"+args[3]+"\n"+args[4]+"\n"+args[5]);

            return cal_data(resultString);
        }

        String resultString=args[0];
        pln("strParam 첫번째 인자\n"+args[0]);
        for(int i=1;i<args.length;i++){
            resultString=resultString+"\n"+args[i];
        }
        resultString=resultString+"\nEOF";
        if(args[0].equals("add_data")&&args.length==3){
            pln("add_data에 들어옴:"+resultString);
            List<String> result=save_data(resultString);
            // for(String str : result) {
            //     pln(str);
            // }
            return result;
        }
        if(args[0].equals("cal_data")&&args.length==5){
            List<String> result=cal_data(resultString);
            // for(String str : result) {
            //     pln(str);
            // }
            return result;
        }
        if (args[0].equals("find_period")&&args.length==3){
            return find_period(resultString);
        }

        return null;
    }


    List<String> find_period(String data){

        // 파이썬 스크립트 실행

//            pp = pb.start();
        pln("find_period 실행");
        // 파이썬 스크립트에 데이터 전달
//        OutputStream outputStream = getOs();
        PrintWriter writer = getPw();
//            data = "cal_data\n1008\nIBM\n20130101\n20140101";
        writer.println(data);
        writer.flush();
//        writer.close();
        pln("입력완료");

        // 파이썬 스크립트의 출력 읽어오기
        List<String> result= new ArrayList<>();
        String line;
        try{
//            InputStream is = getIs();
            BufferedReader reader = getBr();
            pln("br획득");
            while ((line = reader.readLine()) != null) {
                pln("python add 출력: "+line);
                if (line.equals("EOF")){
                    pln("연결을 끊습니다.");
                    pln("명령어는 "+data);
                    break;
                }

                result.add(line);
            }

            // 프로세스 종료
            pln("연결을 마칩니다.");
            return result;
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            return null;
        }
    }
    void pln(String str){
        System.out.println(str);
    }
    List<String> save_data(String data){

        // 파이썬 스크립트 실행

//            pp = pb.start();
        pln("add_data 실행");
        // 파이썬 스크립트에 데이터 전달
//        OutputStream outputStream = getOs();
        PrintWriter writer = getPw();
//            data = "cal_data\n1008\nIBM\n20130101\n20140101";
        writer.println(data);
        writer.flush();
//        writer.close();
        pln("입력완료");

        // 파이썬 스크립트의 출력 읽어오기
        List<String> result= new ArrayList<>();
        String line;
        try{
//            InputStream is = getIs();
            BufferedReader reader = getBr();
            pln("br획득");
            while ((line = reader.readLine()) != null) {
                pln("python add 출력: "+line);
                if (line.equals("EOF")){
                    pln("연결을 끊습니다.");
                    pln("명령어는 "+data);
                    break;
                }

                result.add(line);
            }

            // 프로세스 종료
            pln("연결을 마칩니다.");
            return result;
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            return null;
        }
    }

    List<String> cal_data(String data){
        pln("cal_data 실행");
        // 파이썬 스크립트 실행
//        ProcessBuilder pb = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py");
//        Process pp=getPythonProcess();
        // try{
//            pp = pb.start();
        // 파이썬 스크립트에 데이터 전달

        PrintWriter writer = getPw();
//            data = "cal_data\n1008\nIBM\n20130101\n20140101";
        writer.println(data);
        writer.flush();
//            outputStream.close();
        pln("입력완료");
        // }catch(IOException ie){
        //     System.out.println("ie: "+ie);
        //     ie.printStackTrace();
        // }
        // 파이썬 스크립트의 출력 읽어오기
        String line;
        List<String> result = new ArrayList<>();
        try{
            BufferedReader reader = getBr();
            pln("출력시작");
            while ((line = reader.readLine()) != null) {
                pln("python cal 출력: "+line);
                if (line.equals("EOF")){
                    pln("연결을 끊습니다.");
                    pln("명령어는 "+data);
                    break;
                }
                result.add(line);
            }
            pln("출력완료");
            // 프로세스 종료
            pln("연결을 마칩니다.");
            pln("리턴값은");
            for(String str:result){
                pln(str);
            }
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
        }else if(maxIndex>0&&maxIndex<4){
            result=result+"두 데이터는 "+maxIndex+"주의 간격을 두고 전파되는 관계에 있습니다.\n";
        }else{
            result=result+"두 데이터는 4주 이상의 간격을 두고 전파되는 관계에 있습니다.\n";
        }
        return result;
    }
}