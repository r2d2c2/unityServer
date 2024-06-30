package study.unityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UnityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnityServerApplication.class, args);
    }

}
