package com.archisemtle.semtlewebserverspring.vo;

import java.util.List;
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
    private List<String> roles;
    private boolean manageApprovalStatus;

    public static LoginResponseVo dtoToVo(LoginResponseDto loginResponseDto) {
        return LoginResponseVo.builder()
            .uuid(loginResponseDto.getUuid())
            .accessToken(loginResponseDto.getAccessToken())
            .refreshToken(loginResponseDto.getRefreshToken())
            .username(loginResponseDto.getUsername())
            .roles(loginResponseDto.getRoles())
            .manageApprovalStatus(loginResponseDto.isManageApprovalStatus())
            .build();
    }
}
