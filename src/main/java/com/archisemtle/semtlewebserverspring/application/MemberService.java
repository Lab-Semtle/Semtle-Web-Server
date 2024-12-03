package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.MemberRegistrationDto;
import com.archisemtle.semtlewebserverspring.dto.MemberFirstSaveDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member save(MemberRegistrationDto memberRegistrationDto) {
       return memberRepository.save(memberRegistrationDto.toEntity(passwordEncoder));
    }

    @Transactional
    public Member firstSave(MemberFirstSaveDto memberFirstSaveDto) {
        return memberRepository.save(memberFirstSaveDto.toEntity());
    }
}
