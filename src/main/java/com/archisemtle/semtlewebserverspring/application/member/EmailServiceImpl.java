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
    public void sendPasswordResetEmail(String to, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("비밀번호 재설정 안내");
            helper.setText(buildEmailContent(resetLink), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패", e);
        }
    }

    private String buildEmailContent(String resetLink) {
        return "<h2>비밀번호 재설정 안내</h2>" +
            "<p>아래 버튼을 클릭하여 비밀번호를 재설정하세요.</p>" +
            "<p><a href='" + resetLink + "' style='display:inline-block;padding:10px 20px;color:white;background-color:#007bff;text-decoration:none;border-radius:5px;'>비밀번호 재설정</a></p>" +
            "<p>이 링크는 15분 후 만료됩니다.</p>";
    }
}
