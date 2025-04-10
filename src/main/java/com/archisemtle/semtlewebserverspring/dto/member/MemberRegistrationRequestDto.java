package com.archisemtle.semtlewebserverspring.dto.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberRegistrationRequestDto {

    private String email;
    private String password;
    private String username;
    private String studentId;
    private String phone;
    private LocalDate birth;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .uuid(UUID.randomUUID())
            .email(email)
            .password(passwordEncoder.encode(password))
            .studentId(studentId)
            .username(username)
            .phone(phone)
            .role("USER")
            .manageApprovalStatus(true)
            .build();
    }
}
