package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailResponseDto;

public interface PasswordResetService {
    MemberPasswordResetEmailResponseDto sendPasswordResetEmail(String email);
    void resetPassword(String token, String currentPassword, String newPassword, String confirmNewPassword);
    String generateResetToken();
}
