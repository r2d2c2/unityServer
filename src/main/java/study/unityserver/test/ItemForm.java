package study.unityserver.test;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {//웹에서 업로드할 데이터
    private Long itemId;
    private String itemName;//상품명
    private MultipartFile attachFile;//파일
    private List<MultipartFile> imageFiles;//여러 파일
}
