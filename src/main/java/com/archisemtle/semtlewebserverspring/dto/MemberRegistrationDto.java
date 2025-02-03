package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Member;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class MemberRegistrationDto {
    private String studentId;
    private String password;

    @Builder
    public MemberRegistrationDto(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .uuid(UUID.randomUUID())
                .studentId(studentId)
                .password(passwordEncoder.encode(password))
                .roles(List.of("ROLE_USER"))
                .manageApprovalStatus(false)
                .build();
    }
}
