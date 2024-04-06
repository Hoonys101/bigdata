package main.python;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class JavaPythonExample {
    public static void main(String[] args){
        JavaPythonExample connect= new JavaPythonExample();
        ArrayList<String> list = new ArrayList<>();
        list.add("004710");
        list.add("KOSPI");

        System.out.println(connect.connectPython("add_data", list));
    }
    List<String> connectPython(String methodName, List<String> parameter) {
        ProcessBuilder pb = new ProcessBuilder("python", "python\\controller.py");
        try{
            Process p = pb.start();
            OutputStream outStream = p.getOutputStream();
            PrintWriter writer = new PrintWriter(outStream);
            String data = methodName;
            for (String str:parameter)
                data=data+"\n"+str;
            writer.println(data);
            writer.flush();
            writer.close();
            outStream.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            List<String> result = new ArrayList<>();
            String ret="";
            while ((ret = in.readLine())!=null)
                result.add(ret);
            in.close();
            p.onExit();
            return result;
        }catch(IOException ie){
            System.out.println("ie: "+ie);
            return null;
        }
    }
}