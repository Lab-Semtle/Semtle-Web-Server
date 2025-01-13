package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.ShowMember;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShowMemberResponseDto {
    private String name;
    private Date birth;
    private String phone;

    public static ShowMemberResponseDto entityToDto(ShowMember showMember) {
        return ShowMemberResponseDto.builder()
            .name(showMember.getName())
            .birth(showMember.getBirth())
            .phone(showMember.getPhone())
            .build();
    }
}
