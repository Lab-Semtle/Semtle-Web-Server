package com.archisemtle.semtlewebserverspring.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 특정 URL 경로에만 CORS 적용 (예: /api/**)
            .allowedOriginPatterns("http://localhost:3000",
                "https://archisemtle.com",
                "https://archisemtle.site")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true); // 쿠키와 같은 인증 정보를 포함하려면 true로 설정
    }

}




