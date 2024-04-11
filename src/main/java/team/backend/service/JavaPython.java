package team.backend.service;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
@Service
public class JavaPython implements JavaPythonInter {
    public static void main(String[] args){
        JavaPython java = new JavaPython();
//        java.save_data();
        java.strParameter("add_data","1008","Index");
        java.strParameter("add_data","IBM","SnP500");
        java.strParameter("cal_data","1008","Index","IBM","SnP500","20130101","20140101");
    }
    @Override
    public boolean strParameter(String... args){
        String resultString=args[0];
        for(int i=1;i<args.length;i++){
            resultString=resultString+"\n"+args[i];
        }
        if(args[0].equals("add_data")&&args.length==3){
            save_data(resultString);
            return true;
        }else if(args[0].equals("cal_data")&&args.length==7){
            cal_data(resultString);
            return true;
        }else if (args[0].equals("diff_cal_data")&&args.length==11){
            cal_data(resultString);
            return true;
        }else{
            pln("명령어 또는 인자수가 다릅니다.");
            return false;
        }
    }
    void pln(String str){
        System.out.println(str);
    }
    void save_data(String data){

        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py");
        Process pp=null;
        try{
            pp = pb.start();
            pln("save_data 실행");
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
//            data = "cal_data\nIndex\n1008\nSnP500\nIBM\n20130101\n20140101";
            writer.println(data);
            writer.flush();
            writer.close();
            outputStream.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
        // 파이썬 스크립트의 출력 읽어오기
        String line="";
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream(),"cp949"));
            while ((line = reader.readLine()) != null) {
                System.out.println("Python output: " + line);
            }

            // 프로세스 종료
            reader.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
    }

    void cal_data(String data){
        pln("cal_data 실행");
        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py");
        Process pp=null;
        try{
            pp = pb.start();
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
//            data = "cal_data\nIndex\n1008\nSnP500\nIBM\n20130101\n20140101";
            writer.println(data);
            writer.flush();
            writer.close();
            outputStream.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            ie.printStackTrace();
        }
        // 파이썬 스크립트의 출력 읽어오기
        String line="";

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream(),"cp949"));
            while ((line = reader.readLine()) != null) {
                System.out.println("Python output: " + line);
            }

            // 프로세스 종료
            reader.close();
            pp.destroy();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
    }
}