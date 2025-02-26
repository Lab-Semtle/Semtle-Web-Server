package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ShowMemberResponseDto;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowMemberResponseVo {
    private String name;
    private Date birth;
    private String phone;

    @Builder
    public ShowMemberResponseVo(String name, Date birth, String phone){
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }

    public static ShowMemberResponseVo dtoToVo(
        ShowMemberResponseDto showMemberResponseDto) {
        return ShowMemberResponseVo.builder()
            .name(showMemberResponseDto.getName())
            .birth(showMemberResponseDto.getBirth())
            .phone(showMemberResponseDto.getPhone())
            .build();
    }
}
