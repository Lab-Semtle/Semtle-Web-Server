package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberRequestDto memberRequestDto) {
//        return memberRepository.save(memberRequestDto.toEntity());
        return ;
    }

    // Member 조회 메서드
    public Member findById(int id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id ,MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(id).orElse(null);
        // DTO에서 가져온 값만 업데이트
        if (memberRequestDto.getName() != null) {
            member.setName(memberRequestDto.getName());
        }
        if (memberRequestDto.getBirth() != null) {
            member.setBirth(memberRequestDto.getBirth());
        }
        if (memberRequestDto.getPhone() != null) {
            member.setPhone(memberRequestDto.getPhone());
        }
        if (memberRequestDto.getStudentId() != null) {
            member.setStudentId(memberRequestDto.getStudentId());
        }
//        if (memberRequestDto.getImageUrl() != null) {
//            member.setImageUrl(memberRequestDto.getImageUrl());
//        }
        memberRepository.save(member);
    }
}
