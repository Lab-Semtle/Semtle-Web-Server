package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.UpdateMember;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberResponseDto;
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
    @Transactional
    public Member show(UUID uuid) {
        return memberRepository.findByUuid(uuid).orElse(null);
    }

    // Member 수정 메서드
    @Transactional
    public UpdateMemberResponseDto update(UUID uuid , UpdateMemberRequestDto updateMemberRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElse(null);

        UpdateMember updateMember = UpdateMember.builder()
            .name(updateMemberRequestDto.getName() != null ? updateMemberRequestDto.getName() : member.getName())
            .birth(updateMemberRequestDto.getBirth() != null ? updateMemberRequestDto.getBirth() : member.getBirth())
            .phone(updateMemberRequestDto.getPhone() != null ? updateMemberRequestDto.getPhone() : member.getPhone())
            .build();

        member.setName(updateMember.getName());
        member.setBirth(updateMember.getBirth());
        member.setPhone(updateMember.getPhone());

        memberRepository.save(member);
        return UpdateMemberResponseDto.entityToDto(updateMember);
    }
}
