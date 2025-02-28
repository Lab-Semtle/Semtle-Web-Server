package com.archisemtle.semtlewebserverspring.vo.member;

import com.archisemtle.semtlewebserverspring.dto.member.LoginResponseDto;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseVo {
    private UUID uuid;
    private String accessToken;
    private String refreshToken;
    private String username;

    public static LoginResponseVo dtoToVo(LoginResponseDto loginResponseDto) {
        return LoginResponseVo.builder()
            .uuid(loginResponseDto.getUuid())
            .accessToken(loginResponseDto.getAccessToken())
            .refreshToken(loginResponseDto.getRefreshToken())
            .username(loginResponseDto.getUsername())
            .build();
    }
}
