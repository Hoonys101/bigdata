/*package team.backend.service;
import java.io.*;
import java.util.*;

public class JavaPython implements JavaPythonInter {
    public static void main(String[] args){
        JavaPython java = new JavaPython();
//        java.save_data();
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
        }else if (args[0].equals("diff_cal_data")&&args.length==9){
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
        ProcessBuilder pb = new ProcessBuilder("python", "python\\controller.py");
        Process pp=null;
        try{
            pp = pb.start();
            
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
//            data = "cal_data\n1008\nIndex\nIBM\nSnP500\n20130101\n20140101";
            writer.println(data);
            writer.flush();
            writer.close();
            outputStream.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
        // 파이썬 스크립트의 출력 읽어오기
        BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream()));
        String line="";
        try{
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

        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "python\\controller.py");
        Process pp=null;
        try{
            pp = pb.start();
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
//            data = "cal_data\n1008\nIndex\nIBM\nSnP500\n20130101\n20140101";
            writer.println(data);
            writer.flush();
            writer.close();
            outputStream.close();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            ie.printStackTrace();
        }
        // 파이썬 스크립트의 출력 읽어오기
        BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream()));
        String line="";
        try{
            pln("파이썬 출력을 출력합니다.");
            while ((line = reader.readLine()) != null) {
                System.out.println("Python output: " + line);
            }

            // 프로세스 종료
            reader.close();
            pp.destroy();
            pln("파이썬 프로그램 종료 완료");
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
    }
}*/
