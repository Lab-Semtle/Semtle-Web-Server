package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
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
    public Member findByUUID(UUID uuid) {
        return memberRepository.findByUuid(uuid).orElse(null);
    }

    @Transactional
    public void update(UUID uuid , UpdateMemberRequestDto updateMemberRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElse(null);

        Member updateMember = updateMemberRequestDto.toEntity();

        // DTO에서 가져온 값만 업데이트
        if (updateMember.getName() != null) {
            member.setName(updateMember.getName());
        }
        if (updateMember.getBirth() != null) {
            member.setBirth(updateMember.getBirth());
        }
        if (updateMember.getPhone() != null) {
            member.setPhone(updateMember.getPhone());
        }
//        if (memberRequestDto.getImageUrl() != null) {
//            member.setImageUrl(memberRequestDto.getImageUrl());
//        }
        memberRepository.save(member);
    }
}
