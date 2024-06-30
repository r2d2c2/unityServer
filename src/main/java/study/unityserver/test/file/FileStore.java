package study.unityserver.test.file;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import study.unityserver.test.domain.UploadFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${filedir}")
    private String filedir;
    public String getFullPath(String filename){
        return filedir+filename;//파일 경로+파일 이름
    }
    @SneakyThrows
    public UploadFile storeFile(MultipartFile multipartFile){
        //하나의 파일을 저장
        if(multipartFile.isEmpty()){
            return null;//업로드한 파일이 없으면 null
        }
        //업로드한 파일이 있으면 파일 이름을 저장
        String originalFilename= multipartFile.getOriginalFilename();
        // 서버 이전 파일 이름

        int pos = originalFilename.lastIndexOf(".");
        // 파일이름에서  .png 같은 확장자만 따로 저장
        String ext = originalFilename.substring(pos + 1);


        String uuid= UUID.randomUUID().toString();
        //서버에 저장하는 파일명 (파일명이 같으것이 있으면 덮어 쓰기로 저장된다)
        //근대 확장자 까지 다시 작성해서 어떤 파일인지 확인 할수 없다

        String storeFileName=uuid+"."+ext;
        //그래서 uuid에서 따로 저장한 확장자 추가 하여 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename,storeFileName);
        // 서버 업로드 이전 파일이름과 서버에 저장하는 파일이름 저장
    }
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles){
        //여러개의 파일을 저장
        List<UploadFile> stroreFileResult=new ArrayList<>();
        //리스트로 파일들을 저장할 변수
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){//업로드한 파일이 있으면
                UploadFile uploadFile = storeFile(multipartFile);
                //하나식 파일저장 처리하고
                stroreFileResult.add(uploadFile );
                //리스트로 담기
            }
        }
        return stroreFileResult;//저장한 파일들을 리턴
    }
}
