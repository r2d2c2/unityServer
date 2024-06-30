package study.unityserver.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@Log4j2
@RestController
@RequestMapping("/py")
public class PythonAndJava {

    @GetMapping("/text")
    public String inputText(){
        String  pyurl="http://127.0.0.1:7070/pytext";
        String text="심장이 아파";
        RestTemplate restTemplate=new RestTemplate( );
        String  send= restTemplate.postForObject(pyurl,text, String.class);
        // 주소,바디값,매핑 타입
        log.info(send);

        return send;
    }
    @GetMapping("/text2")
    public String inputText2(){
    String  pyurl="http://127.0.0.1:7070/pytext?inputText=심장이 아파";
    RestTemplate restTemplate=new RestTemplate( );
    String  send= restTemplate.getForObject(pyurl, String.class);
    // 주소,바디값,매핑 타입
    log.info(send);

    return send;
}

}
