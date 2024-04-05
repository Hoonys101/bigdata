import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class javapython {
    public static void main(String[] args) throws IOException, InterruptedException{
        //        ProcessBuilder pb2 = new ProcessBuilder("python", "Repository.py");
/*        ProcessBuilder pb2 = new ProcessBuilder("python", "finance_reader.py");
        System.out.println("1");
        Process pp2 = pb2.start();
        System.out.println("2");
        OutputStream outputStream = pp2.getOutputStream();
        PrintWriter writers = new PrintWriter(outputStream);
        String datas = "hello\nnaver\ngood\nto\nsee\nyou";
        System.out.println("3");
        writers.println(datas);
        System.out.println("4");
        writers.flush();
        writers.close();
        outputStream.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(pp2.getInputStream()));
        System.out.println("5");
        String rett="";
        try{
            while ((rett = reader.readLine())!=null)
                System.out.println("value is : "+rett);
            reader.close();
            pp2.onExit();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
        System.out.println("6");
         */
        // 파이썬 스크립트 실행
        ProcessBuilder pb = new ProcessBuilder("python", "controller.py");
        Process pp = pb.start();
        
        // 파이썬 스크립트에 데이터 전달
        OutputStream outputStream = pp.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        String data = "add_data\nKOSPI\n004710";
        writer.println(data);
        writer.flush();
        writer.close();
        
        // 파이썬 스크립트의 출력 읽어오기
        BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream()));
        String line="";
        try{
            while ((line = reader.readLine()) != null) {
                System.out.println("Python output: " + line);
            }
            
        // 프로세스 종료
        reader.close();
        outputStream.close();
        pp.onExit();
        }catch(IOException ie){
            System.out.println("ie: "+ie);
        }
    }
}
