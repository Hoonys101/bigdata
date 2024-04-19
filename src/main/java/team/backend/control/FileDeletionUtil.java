package team.backend.control;

import java.io.File;

public class FileDeletionUtil {

    public static void deleteFiles(String directoryPath) {
        File directory = new File(directoryPath);

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
    }
}