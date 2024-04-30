package team.backend.control;

import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDeletionUtil {

    public static void deleteFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File directoryStatic = new File(directoryPath);

        // 해당 디렉토리가 존재하고, 디렉토리인 경우에만 파일을 삭제합니다.
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 파일을 삭제합니다.
                    file.delete();
                }
            }
        }
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 파일을 삭제합니다.
                    file.delete();
                }
            }
        }
    }
    public static void deleteSelectedFile(List<String> filesName){
        // 삭제할 파일의 경로와 이름 지정
        // File 객체 생성
        List<File> files= new ArrayList<>();
        for(String fileName:filesName){
            files.add(new File(fileName));
        }
        for(File file:files){
            // 파일이 존재하는지 확인 후 삭제
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("파일이 성공적으로 삭제되었습니다.");
                } else {
                    System.out.println("파일을 삭제하는데 실패했습니다.");
                }
            } else {
                System.out.println("삭제할 파일이 존재하지 않습니다.");
            }
        }
    }
}