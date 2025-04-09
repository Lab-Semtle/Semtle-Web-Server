package com.archisemtle.semtlewebserverspring.application.member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("비밀번호 재설정 안내");
            helper.setText(buildEmailContent(resetToken), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패", e);
        }
    }

    private String buildEmailContent(String resetToken) {
        return "<h2>비밀번호 재설정 안내</h2>" +
            "<p>아래 코드를 기입하여 비밀번호를 재설정하세요.</p>" +
            "<div style=\"border: 2px solid #4CAF50; " +
            "padding: 10px; " +
            "width: fit-content; " +
            "font-size: 24px; " +
            "font-weight: bold; " +
            "color: #333; " +
            "background-color: #f9f9f9; " +
            "border-radius: 8px; " +
            "margin: 16px 0; " +
            "text-align: center;\">" +
            resetToken +
            "</div>" +
            "<p>이 코드는 15분 후 만료됩니다.</p>";
    }
}
