package study.unityserver.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import study.unityserver.repository.Openai_logRepository;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UnityAndOpenai {
    private final Openai_logRepository openaiLogRepository;
    private final OpenAiChatModel openAiChatModel;



    @GetMapping("/testai/{test}")
    public String getOpenAiChatModel(@PathVariable("test")String test) {

        return test;
    }
    /*@PostMapping("/test")
    public String postOpenAiChatModel(@RequestBody ChatDataDTO chatDataDTO) {

    }*/
}
