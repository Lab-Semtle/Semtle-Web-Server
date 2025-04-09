package com.archisemtle.semtlewebserverspring.application.member;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetToken);
}
