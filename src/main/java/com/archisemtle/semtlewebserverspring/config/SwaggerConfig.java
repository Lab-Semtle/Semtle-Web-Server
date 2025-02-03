package com.archisemtle.semtlewebserverspring.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    // API 그룹 설정 (Optional)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Public APIs") // 그룹 이름
                .pathsToMatch("/api/**") // 특정 경로만 문서화
                .build();
    }

    // OpenAPI 정보 설정
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("Archisemtle Test API")
                        .version("2.0.2")
                        .description("API documentation using springdoc-openapi 2.0.2")
                        .contact(new Contact()
                                .name("archisemtle"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local server")
                ));
    }
}