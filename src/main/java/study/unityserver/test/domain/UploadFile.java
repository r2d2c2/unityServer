package study.unityserver.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//생성자 추가
public class UploadFile {
    private String uploadFileName;//업로드한 파일명
    private String storeFileName;//시스템에 있는 파일명
}
