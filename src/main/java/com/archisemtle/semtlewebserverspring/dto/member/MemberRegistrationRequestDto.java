package com.archisemtle.semtlewebserverspring.dto.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberRegistrationRequestDto {

    private String email;
    private String password;
    private String role;
    private boolean manageApprovalStatus;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .uuid(UUID.randomUUID())
            .email(email)
            .password(passwordEncoder.encode(password))
            .role(role)
            .manageApprovalStatus(manageApprovalStatus)
            .build();
    }
}
