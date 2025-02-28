package com.archisemtle.semtlewebserverspring.config;

import com.archisemtle.semtlewebserverspring.config.jwt.JwtAuthenticationFilter;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // JWT 관련 유틸리티 클래스

    @Bean
    public CorsConfigurationSource corsConfigurationSource () {
        return request -> {
            var cors= new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)) // Stateless 설정
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.POST, "/api/v1/members", "/auth/signin").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/index/**", "/api/v1/projectboard/**","/promotions/**", "api/v1/activity/**").permitAll()

                .requestMatchers(
                    "/swagger-ui/**",       // Swagger UI 리소스
                    "/v3/api-docs/**",      // OpenAPI 문서
                    "/v3/api-docs.yaml",    // YAML 형식 문서(필요 시)
                    "/swagger-ui.html",
                    "/health",
                    "/api/v1/projecttypecategory/**"
                ).permitAll() //특정 경로 토큰 인증X

                .anyRequest().authenticated() // 이외에는 토큰 인증 필요
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 BCrypt 사용
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
