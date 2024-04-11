package team.backend.service;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JavaPython implements JavaPythonInter {
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
            save_data(resultString);
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
}