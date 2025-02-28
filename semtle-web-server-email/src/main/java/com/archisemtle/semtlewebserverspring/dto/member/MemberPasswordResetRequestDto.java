package com.archisemtle.semtlewebserverspring.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPasswordResetRequestDto {
    String token;
    String currentPassword;
    String newPassword;
    String confirmNewPassword;
}
