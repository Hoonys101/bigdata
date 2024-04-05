import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class Copy {


    public void copy1(File source, File destination) throws IOException{
        try {
            System.out.println("1. 복사");
            System.out.println("2. 잘라내기");
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            int num = Integer.parseInt(str);

            if (num == 1) {
                copy2(source, destination);
            } else if (num == 2) {
                System.out.println("잘라낼 원본파일 : ");
                // copy3(source, destination);
            }
        }catch (IOException ie){

        }
    }



    public void copy2(File source, File destination) throws IOException {
        try {
            System.out.println("복사할 파일을 입력하세요 : ");
            FileInputStream fileInputStream = new FileInputStream(source);
            FileOutputStream fileOutputStream = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {

                fileOutputStream.write(buffer, 0, length);
            }

            fileInputStream.close();
            fileOutputStream.close();
            System.out.println("파일이 복사되었습니다.");

            System.out.println("복사할 폴더를 입력하세요 : ");


            //if (!file.exists() || !file.isFile()) {
            //  System.out.println("파일이 존재하지 않거나 파일이 아닙니다.");
            //return;
            //}

            // folderStr = sc.nextLine();
            //File folder = new File(folderStr);
            //if (!folder.exists() || !folder.isDirectory()) {
            //System.out.println("폴더가 존재하지 않거나 폴더가 아닙니다.");
            //return;
            //}
            }catch (Exception e){}
        }

    //public void copy3(File source, File destination) throws IOException{
        //System.out.println("잘라낼 파일을 입력하세요 : ");
   // }
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String sourcePath = scanner.nextLine();
        File source = new File(sourcePath);
        ;
        String destinationPath = scanner.nextLine();
        File destination = new File(destinationPath);

        Copy copy = new Copy();
        copy.copy1(source, destination);
    }
}
