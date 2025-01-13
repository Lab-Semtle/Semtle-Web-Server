package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Member;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberRequestDto {

    private String name;
    private Date birth;
    private String phone;
    //private String imageUrl;

    @Builder
    public UpdateMemberRequestDto(String name, Date birth, String phone) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }

    public Member toEntity() {
        return Member.builder()
            .name(name)
            .birth(birth)
            .phone(phone)
            .build();
    }
}
