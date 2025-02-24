package com.archisemtle.semtlewebserverspring.dto;

import java.util.List;
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
    private List<String> roles;
    private boolean manageApprovalStatus;
}
