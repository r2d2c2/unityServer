package study.unityserver.test.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {//상품관리
    private Long id;
    private String itemName;//상품의 이름
    private UploadFile attachFile;//첨부파일 하나
    private List<UploadFile> imageFiles;//여러게의 이미지
}
