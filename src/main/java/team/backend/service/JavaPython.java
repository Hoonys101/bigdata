package team.backend.service;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
@Service
public class JavaPython implements JavaPythonInter {

    @Getter
    private static BufferedReader br;
    private static Process pythonProcess;
    private int currentIndex=0;
    private List<List<String>> results=new ArrayList<List<String>>();
//    private static ThreadLocal<List<String>> threadResult= new ThreadLocal<>();
    private ThreadLocal<Object> lock=new ThreadLocal<>();
    public synchronized int assignIndex(){
        if (currentIndex==10000){
            currentIndex=0;
        }
        return currentIndex++;
    }
    @Getter
    private static PrintWriter pw;
    private JavaPython(){
        try{
            pythonProcess = new ProcessBuilder("python", "src\\main\\java\\team\\backend\\service\\python\\controller.py").start();
            br = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream(),"cp949"));
            pw= new PrintWriter(pythonProcess.getOutputStream());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                JavaPython.pythonProcess.destroy();
            }));
        }catch(IOException ie){
            System.out.println("생성중 에러 발생 ie: "+ie);
        }
        new Listener().start();
        

    }

    public static void main(String[] args){

        JavaPython java = new JavaPython();

        // System.out.println("find_period");
//        List<String> results=java.strParameter("add_data","Index","1001");
//        List<String> results=java.strParameter("find_period","1152","1153");
//        List<String> results=java.strParameter("diff_cal_data","1152","1008","1153","1008","20130101","20130501");

        List<String> results=java.strParameter("cal_data","1152","1153","20130101","20130501");
//        List<String> results=java.strParameter("tree_data","1152","1153");
        System.out.println("result printing");
        for(String result:results){
            System.out.println(result);
        }
        results=java.strParameter("cal_data","1153","1152","20130101","20130501");
//        List<String> results=java.strParameter("tree_data","1152","1153");

        System.out.println("result printing");
        for(String result:results){
            System.out.println(result);
        }
    }


    // 인자를 이름과 함께 파이썬으로 넘기는 메소드
    // 이름과 함께 리스너에 등록하고 정지 메소드
    // 리스너의 전파에따라 결과값에서 자신의 결과를 찾고, 결과값에서 자신의 결과를 삭제하는 메소드
    // 무한루프로, 파이썬의 결과값을 결과값에 저장하는 옵저버 객체 -> 결과값을 저장할 때마다, 등록된 리스너에 전파


    //results 중 자신의 답을 찾고, 프로세스 재개하는 리스너
    private void findResult(String threadName, Consumer<String> listener, Object lock){
        for(List<String> result:results){
            pln("결과값들에서 맞는 결과값을 찾습니다.찾는 thread이름은 "+threadName);
            pln("결과 중 이번 결과의 thread이름은 "+ result.get(0) );
            if(result.get(0).equals(threadName)){
                pln("결과값이 존재합니다. lock값은 "+lock.toString());
                synchronized (lock){
                    lock.notify();
                }
                Listener.removeListener(listener);
                return;
            }
        pln("결과값이 없습니다.");
        }

    }


    //옵저버
    class Listen implements Consumer<String> {
        private final Object lock;
        private final String threadName;
        Listen(Object lock,String threadName){
            this.lock=lock;
            this.threadName=threadName;
        }
        @Override
        public void accept(String resultFirst) {
            findResult(this.threadName, this,lock);
        }
    }
    private synchronized void toPython(String data){
        PrintWriter writer = getPw();
        writer.println(data);
        writer.flush();
        pln("입력완료");
    }

    public List<String> strParameter(String... args){
        lock.set(new Object());
//        pln(lock.get().toString());
        List<String> start=null;
        List<String> finalresult=null;
        String threadName=String.valueOf(assignIndex());
        pln("threadName: "+threadName);
        if (args[0].equals("diff_cal_data")&&(args.length==7)||args[0].equals("add_data")&&args.length==3||args[0].equals("cal_data")&&args.length==5||args[0].equals("find_period")&&args.length==3||args[0].equals("tree_data")&&args.length==3){
            String resultString=threadName+"\n"+args[0];
            pln("strParam 첫번째 인자\n"+args[0]);
            for(int i=1;i<args.length;i++){
                resultString=resultString+"\n"+args[i];
            }
            resultString=resultString+"\n"+"EOF";
            pln("resultString: "+resultString);
            toPython(resultString);
        }
        else{
            pln("인자의 수 또는 종류가 다릅니다.");
        }
        Listener.addListener(new Listen(lock.get(),threadName));
        pln("리스너 등록");
        synchronized (lock.get()){

            try{
                pln("기다리기시작");
                lock.get().wait();
                pln("찾기");
                for(int i=0;i<results.size();i++){
                    if(results.get(i).get(0).equals(threadName)){
                        finalresult=results.get(i);
                        results.remove(i);
                        break;
                    }
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            pln("재개");
        }
        pln("finalresult: ");
         finalresult.remove(0);
          return finalresult;


    }
    void pln(String str){
        System.out.println(str);
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

    //fromPython
    class Listener extends Thread{
        private static List<Consumer<String>> Listeners = new CopyOnWriteArrayList<>();
        public static void addListener(Consumer<String> listener){
            Listeners.add(listener);
        }
        public static void removeListener(Consumer<String> listener){
            Listeners.remove(listener);
        }

        public void run(){
            pln("파이썬 데이터 출력 준비");
            // 파이썬 스크립트의 출력 읽어오기

            while(true){
                List<String> result= new ArrayList<>();
                String line;
                try{
        //            InputStream is = getIs();
                    BufferedReader reader = getBr();
                    pln("br획득");
                    while ((line = reader.readLine()) != null) {

//                        pln("python 출력: "+line);
                        if (line.equals("EOF")){
                            pln("연결을 끊습니다.");
                            break;
                        }
                        pln("파이썬 출력: "+line);
                        result.add(line);
                    }
        
                    // 프로세스 종료
                    pln("연결을 마칩니다.");
                    results.add(result);
                    pln("전체 출력값은 :(results사이즈)");
                    for(List<String> resultEach:results){
                        pln("결과 출력");
                        for(String resultline:resultEach){
                            pln(resultline);
                        }
                        pln("result사이즈: "+results.size());
                    }
                }catch(IOException ie){
                    System.out.println("ie: "+ie);
                }
                pln("출력완료");
                for(Consumer<String> listener:Listeners){
//                    pln(result.get(0));
                    listener.accept(result.get(0));
                }
            }
        }
    }
}