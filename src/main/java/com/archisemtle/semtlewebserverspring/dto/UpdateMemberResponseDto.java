package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.UpdateMember;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateMemberResponseDto {

    private String name;
    private Date birth;
    private String phone;

    public static UpdateMemberResponseDto entityToDto(UpdateMember updateMember) {
        return UpdateMemberResponseDto.builder()
            .name(updateMember.getName())
            .birth(updateMember.getBirth())
            .phone(updateMember.getPhone())
            .build();
    }
}
