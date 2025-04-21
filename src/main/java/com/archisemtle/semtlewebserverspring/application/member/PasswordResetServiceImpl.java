package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService{

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> tokenStore = new HashMap<>();
    private final Map<String, Instant> tokenExpirationStore = new HashMap<>();

    @Override
    public MemberPasswordResetEmailResponseDto sendPasswordResetEmail(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("해당 이메일의 회원이 없습니다."));

        String resetToken = UUID.randomUUID().toString();
        Instant expirationTime = Instant.now().plusSeconds(900);

        tokenStore.put(resetToken, email);
        tokenExpirationStore.put(resetToken, expirationTime);

        String resetLink = "https://example.com/password-reset?token=" + resetToken;
        emailService.sendPasswordResetEmail(member.getEmail(), resetLink);

        return new MemberPasswordResetEmailResponseDto(expirationTime.toString());
    }

    @Override
    public void resetPassword(String token, String currentPassword, String newPassword, String confirmNewPassword) {
        if (!tokenStore.containsKey(token) || tokenExpirationStore.get(token).isBefore(Instant.now())) {
            throw new RuntimeException("유효하지 않거나 만료된 토큰입니다.");
        }

        String email = tokenStore.get(token);
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("해당 토큰과 연결된 회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("새로운 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);

        tokenStore.remove(token);
        tokenExpirationStore.remove(token);
    }

    @Override
    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}
