package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Member;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberRegistrationRequestDto {

    private String email;
    private String password;

    @Builder
    public MemberRegistrationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .uuid(UUID.randomUUID())
            .email(email)
            .password(passwordEncoder.encode(password))
            .roles(List.of("USER"))
            .manageApprovalStatus(false)
            .build();
    }
}
