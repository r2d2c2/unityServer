package study.unityserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import study.unityserver.dto.ChatDataDTO;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/unity")
@Log4j2
public class UnityChatController {
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
        if(chatDataDTOs.size()==7)//7개 이상 싸이면
            chatDataDTOs.removeFirst();//가장 첫번째 원소 제거
        return new Result(chatDataDTOs);
    }
    @GetMapping("/chat")
    public Result testouput(){
        chatDataDTOs.add(new ChatDataDTO("asdf","asdf","asdf"));
        //json test
        return new Result(chatDataDTOs);

    }
}

