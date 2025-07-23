package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.MemberSignupRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void memberSignup(MemberSignupRequestDto memberSignupRequestDto) {
        memberRepository.findByEmailOrStudentId(memberSignupRequestDto.getEmail(),
                memberSignupRequestDto.getStudentId())
            .ifPresent(member -> {
                if (member.getEmail().equals(memberSignupRequestDto.getEmail())) {
                    throw new BaseException(BaseResponseStatus.DUPLICATE_EMAIL);
                }
                if (member.getStudentId().equals(memberSignupRequestDto.getStudentId())) {
                    throw new BaseException(BaseResponseStatus.DUPLICATED_MEMBERS);
                }
            });

        Member member = Member.builder()
            .birth(memberSignupRequestDto.getBirth())
            .username(memberSignupRequestDto.getName())
            .uuid(UUID.randomUUID())
            .role("member")
            .studentId(memberSignupRequestDto.getStudentId())
            .email(memberSignupRequestDto.getEmail())
            .password(passwordEncoder.encode(memberSignupRequestDto.getPassword()))
            .build();

        memberRepository.save(member);
    }
}
