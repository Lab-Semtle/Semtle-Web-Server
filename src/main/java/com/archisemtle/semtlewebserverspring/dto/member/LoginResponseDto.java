package com.archisemtle.semtlewebserverspring.dto.member;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private UUID uuid;
    private String accessToken;
    private String refreshToken;
    private String username;
}
