package study.unityserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import study.unityserver.dto.ChatDataDTO;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/unity")
@Log4j2
public class UnityChatController {
    @Value("${filedir}")
    private String fileDir;

    public String pyuri="http://127.0.0.1:7070";
    private static List<ChatDataDTO> chatDataDTOs=new LinkedList<>();
    //json 감싸기
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T chatDatas;//json 파싱을위해 이름 중요
    }
    @PostMapping("/chat")
    public Result unityChat(@RequestBody ChatDataDTO chatDataDTO){
        log.info("유니티로 받은 네용={}",chatDataDTO);



        chatDataDTOs.add(chatDataDTO);
        if(chatDataDTOs.size()==2)//7개 이상 싸이면
            chatDataDTOs.removeFirst();//가장 첫번째 원소 제거
        return new Result(chatDataDTOs);
    }

    //웹 테스트
    @GetMapping("/chat")
    public Result testouput(){
        chatDataDTOs.add(new ChatDataDTO("asdf","asdf","asdf"));
        //json test
        return new Result(chatDataDTOs);

    }

    // 유니티 에서 post로 값을 받고
    // 파이썬으로 post로 값을 보네고 결과를 리턴
    //리턴된 값을 유니티로
    @PostMapping("/unity_python")
    public String unity_python(@RequestParam("inputStr") String inputStr){
        String  pyurl=pyuri+"/pytext";
        System.out.println(pyurl+" 경로 @@@@@@@@@@@@@@@@@");
        System.out.println(inputStr+" 사용자 입력 @@@@@@@@@@@@@@@@@");
        if(inputStr==null||inputStr.isEmpty())
            return "없음";
        //위 주소로 fastapi에게 post 호출
        RestTemplate restTemplate=new RestTemplate( );
//        String  send= restTemplate.postForObject(pyurl,inputStr, String.class);
        String  send=restTemplate.postForObject(pyurl,inputStr,String.class);
        // 주소,바디값,매핑 타입
        log.info(send);

        return send;//유니티 호출해서 받는 결과
    }

    @PostMapping("/unity_python2")
    public String unity_python2(@RequestParam("inputStr") String inputStr){
        String pyurl = pyuri + "/pytext";
        System.out.println(pyurl + " 경로 @@@@@@@@@@@@@@@@@");
        System.out.println(inputStr + " 사용자 입력 @@@@@@@@@@@@@@@@@");
        if(inputStr == null || inputStr.isEmpty())
            return "없음";

        // RestTemplate 초기화
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeaders 객체 생성 및 Content-Type 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON 형식의 요청 바디 생성
        Map<String, String> map = new HashMap<>();
        map.put("inputStr", inputStr);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(pyurl, entity, String.class);

        // 응답 로깅 및 반환
        log.info(response.getBody());
        return response.getBody(); // 유니티 호출해서 받는 결과
    }

    @PostMapping("/unity_python3")
    public String unity_python3(@RequestParam("inputStr") String inputStr){
        String  pyurl=pyuri+"/pytext?inputStr="+inputStr;
        System.out.println(pyurl+" 경로 @@@@@@@@@@@@@@@@@");
        System.out.println(inputStr+" 사용자 입력 @@@@@@@@@@@@@@@@@");
        if(inputStr==null||inputStr.isEmpty())
            return "없음";
        //위 주소로 fastapi에게 post 호출
        RestTemplate restTemplate=new RestTemplate( );
//        String  send= restTemplate.postForObject(pyurl,inputStr, String.class);
        String  send=restTemplate.getForObject(pyurl,String.class);
        // 주소,바디값,매핑 타입
        log.info(send);

        return send;//유니티 호출해서 받는 결과
    }


    //유니티에서 이미지를 받고 그걸 파이썬으로 넘겨 주고
    //결과 값으로 텍스트와 오디오를 받아서 다시 유니티로 반환
    @PostMapping(value="/uploadImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestParam("image") MultipartFile image) throws Exception {
        String url = "http://127.0.0.1:7070/upload";

        // Create a temporary File
        File convFile = File.createTempFile("image", null);
        image.transferTo(convFile);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set up the request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(convFile));

        // Set up the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the network request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Clean up the temporary file
        convFile.delete();

        return response.getBody();
    }


    @SneakyThrows
    @GetMapping(value = "/audio")
    public ResponseEntity<Resource> audio_upload(){
        System.out.println("파일보네기");
        try {
            // 파일 경로
            String filePath = fileDir + "test.wav";

            // 파일 리소스 생성
            Resource resource = new FileSystemResource(filePath);

            // 파일이 존재하는지 확인
            if (!resource.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // 파일 다운로드를 위한 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.wav");

            // 파일 리소스와 헤더를 포함한 ResponseEntity 반환
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            // 에러 발생 시, 에러 메시지와 함께 INTERNAL_SERVER_ERROR 상태 코드 반환
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/audio2")
    public ResponseEntity<byte[]> getAudioFileAsBinary() {
        try {
            // 파일 경로
            String filePath = fileDir + "test.wav";

            // 파일을 바이트 배열로 변환
            byte[] fileContent = StreamUtils.copyToByteArray(new FileInputStream(filePath));

            // 파일 다운로드를 위한 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.wav");

            // 바이트 배열과 헤더를 포함한 ResponseEntity 반환
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            // 에러 발생 시, 에러 메시지와 함께 INTERNAL_SERVER_ERROR 상태 코드 반환
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
