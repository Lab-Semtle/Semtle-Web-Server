package com.archisemtle.semtlewebserverspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.archisemtle.semtlewebserverspring.domain")
@EnableJpaRepositories(basePackages = "com.archisemtle.semtlewebserverspring.infrastructure")
public class SemtlewebserverspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemtlewebserverspringApplication.class, args);
    }

}