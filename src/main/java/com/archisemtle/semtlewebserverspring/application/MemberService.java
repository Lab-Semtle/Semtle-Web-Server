package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.config.jwt.JwtTokenProvider;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtToken;
import com.archisemtle.semtlewebserverspring.dto.MemberRegistrationDto;
import com.archisemtle.semtlewebserverspring.dto.MemberFirstSaveDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member save(MemberRegistrationDto memberRegistrationDto) {
       return memberRepository.save(memberRegistrationDto.toEntity(passwordEncoder));
    }

    @Transactional
    public Member firstSave(MemberFirstSaveDto memberFirstSaveDto) {
        return memberRepository.save(memberFirstSaveDto.toEntity());
    }

    public JwtToken Login(String studentId, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(studentId, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

            return jwtToken;
        } catch (Exception e) {
            log.error("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }
}
