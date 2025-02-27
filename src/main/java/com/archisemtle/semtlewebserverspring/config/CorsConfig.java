package com.archisemtle.semtlewebserverspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//            .allowedOriginPatterns("*") // allowedOrigins("*") 대신 사용
//            .allowedHeaders(CorsConfiguration.ALL)
//            .allowedMethods(CorsConfiguration.ALL)
//            .exposedHeaders(CorsConfiguration.ALL)
//            .allowCredentials(true);
//    }
}

