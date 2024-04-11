import java.io.*;
import java.util.*;
public class JavaPython {
    public static void main(String[] args){
        JavaPython java = new JavaPython();
        java.save_data();
    }
    void pln(String str){
        System.out.println(str);
    }
    List<String> save_data(){
        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "controller.py");
        Process pp=null;
        String data="";
        try{
            pp = pb.start();

            // 파이썬 스크립트에 데이터 전달
            OutputStream outputStream = pp.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            data = "add_data\n1008\nIndex";

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
        List<String> result = new ArrayList<>();
        try{
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
}