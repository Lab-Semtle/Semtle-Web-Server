package com.example.test.application;

import com.example.test.domain.Member;
import com.example.test.dto.MemberRequestDto;
import com.example.test.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(MemberRequestDto memberRequestDto) {
        return memberRepository.save(memberRequestDto.toEntity());
    }
}
