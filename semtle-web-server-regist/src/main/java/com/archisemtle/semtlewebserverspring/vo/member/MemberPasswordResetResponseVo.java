package com.archisemtle.semtlewebserverspring.vo.member;

import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberPasswordResetResponseVo {
    private String resetLinkExpiration;

    public static MemberPasswordResetResponseVo dtoToVo(
        MemberPasswordResetEmailResponseDto memberPasswordResetEmailResponseDto) {
        return MemberPasswordResetResponseVo
            .builder()
            .resetLinkExpiration(memberPasswordResetEmailResponseDto.getResetLinkExpiration())
            .build();
    }
}
