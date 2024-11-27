package com.example.test.dto;

import com.example.test.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String email;
    private String password;
    private String name;
    private Date birth;
    private String phone;
    private String studentId;

    @Builder
    public MemberRequestDto(String email, String password, String name, Date birth, String phone, String studentId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.studentId = studentId;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .birth(birth)
                .phone(phone)
                .studentId(studentId)
                .build();
    }
}
