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