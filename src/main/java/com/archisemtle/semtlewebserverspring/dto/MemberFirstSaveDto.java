package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberFirstSaveDto {
    private String username;
    private Date birth;
    private String phone;
    private String email;

    @Builder
    public MemberFirstSaveDto(String username, Date birth, String phone, String email) {
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .birth(birth)
                .phone(phone)
                .email(email)
                .build();
    }
}
