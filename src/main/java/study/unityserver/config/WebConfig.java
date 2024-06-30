package study.unityserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override// 유니티 webgl 통신을 위한 설정
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // /** 를 사용 해서 걍 모든 경로에 반영 가능
                        .allowedOrigins("http://211.194.175.189", "https://xn--4k0b998acvh.xn--yq5b.xn--3e0b707e/")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
