import java.io.*;
import java.util.*;
public class JavaPython {
    public static void main(String[] args){
        JavaPython java = new JavaPython();
        java.save_data();
    }
    Stirng strParameter(String[] args){
        for(int i=0;i++;i<args.length){
            
        }

    }
    void pln(String str){
        System.out.println(str);
    }
    void save_data(){
        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "python\\controller.py");
        Process pp=null;
        String data="";
        try{
            pp = pb.start();
            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            data = "cal_data\n1008\nIndex\nIBM\nSnP500\n20130101\n20140101";
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
}