package com.archisemtle.semtlewebserverspring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

        // SecurityScheme을 올바르게 사용
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
            .name(jwt)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
        );

        return new OpenAPI()
            .components(components) // components 설정
            .info(apiInfo())
            .addSecurityItem(securityRequirement);
    }

    // API의 기본 정보 설정
    private Info apiInfo() {
        return new Info()
            .title("API Test") // API의 제목
            .description("Let's practice Swagger UI") // API에 대한 설명
            .version("1.0.0"); // API의 버전
    }
}