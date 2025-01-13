package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectTypeCategoryResponseDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberResponseDto;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberResponseVo {

    private String name;
    private Date birth;
    private String phone;

    @Builder
    public UpdateMemberResponseVo(String name, Date birth, String phone){
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }

    public static UpdateMemberResponseVo dtoToVo(
        UpdateMemberResponseDto updateMemberResponseDto) {
        return UpdateMemberResponseVo.builder()
            .name(updateMemberResponseDto.getName())
            .birth(updateMemberResponseDto.getBirth())
            .phone(updateMemberResponseDto.getPhone())
            .build();
    }
}
