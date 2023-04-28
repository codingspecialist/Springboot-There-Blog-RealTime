package shop.mtcoding.thereblog.core.util;

import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.thereblog.core.exception.ssr.Exception500;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

public class MyFileUtil {

    public static String write(String uploadFolder, MultipartFile file) {
        // 롤링 기법 (사진명을 2023042807401115_random_person.png)
        UUID uuid = UUID.randomUUID();
        String originalFilename = file.getOriginalFilename();
        String uuidFilename = uuid + "_" + originalFilename;
        try {
            // 파일 사이즈 줄이기
            Path filePath = Paths.get(uploadFolder + uuidFilename);
            Files.write(filePath, file.getBytes());
        } catch (Exception e) {
            throw new Exception500("파일 업로드 실패 : "+e.getMessage());
        }
        return uuidFilename;
    }
}
