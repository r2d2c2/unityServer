package study.unityserver.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import study.unityserver.test.domain.Item;
import study.unityserver.test.domain.ItemRepository;
import study.unityserver.test.domain.UploadFile;
import study.unityserver.test.file.FileStore;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form){
        return "item-form";//등록 폼 보여주기
    }
    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes){
        MultipartFile attachFile= itemForm.getAttachFile();
        // html에서 받은 파일을 uploadFile로 저장
        UploadFile uploadFile=fileStore.storeFile(attachFile);

        List<MultipartFile> imageFiles = itemForm.getImageFiles();
        // html 에서 받은 이미지들 리스트로 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(imageFiles);

        //데이터 베이스 저장
        Item item=new Item();
        item.setItemName(itemForm.getItemName());
        item.setAttachFile(uploadFile);
        item.setImageFiles(uploadFiles);
        itemRepository.save(item    );
        //파일은 따로 폴더에 저장되고 데이터베이스에는 이름만저장

        redirectAttributes.addAttribute("itemId",item.getId());//id를 넘기고
        return "redirect:/items/{itemId}";//페이지 이동
    }
    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model){
        Item item = itemRepository.findById(id);
        model.addAttribute("item",item  );
        return "item-view";
    }
    @ResponseBody
    @GetMapping("/images/{filename}")//html에서 이미지 경로로 보여주기(사진)
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileStore.getFullPath(filename));
        // file:/... 파일.png (경로와 파일이름 합치기)
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        // id 값을 받으면 그 값에 해당 하는 파일만 다운로드 가능
        Item item=itemRepository.findById(itemId    );
        String storeFileName=item.getAttachFile().getStoreFileName();
        //실제 파일명
        String uploadFileName = item.getAttachFile().getUploadFileName();
        //사용자가 업로드한 파일명
        UrlResource urlResource= new UrlResource("file:"+fileStore.getFullPath(storeFileName));
        log.info("uploadFileName={}",uploadFileName);

        //이거 없으면 파일이 다운로으 하는것이 아닌 파일을 웹에서 열기가 된다
        String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        //파일이름이 한글로 되어도 안깨지도록 설정
        String contentDisposition="attachment; filename=\""+encode+"\"";

        /*return ResponseEntity.ok()
                .body(urlResource);*/
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                //http 에게 다운로드 해야하는 파일이라고 설명
                .body(urlResource);
    }

}
