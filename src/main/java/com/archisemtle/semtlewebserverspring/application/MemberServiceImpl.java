package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ShowMember;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ShowMemberResponseDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.vo.ShowMemberResponseVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberRequestDto memberRequestDto) {
//        return memberRepository.save(memberRequestDto.toEntity());
        return ;
    }

    // Member 조회 메서드
    @Override
    public ShowMemberResponseVo show(UUID uuid) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        ShowMember showMember = ShowMember.builder()
            .name(member.getName())
            .birth(member.getBirth())
            .phone(member.getPhone())
            .build();
        ShowMemberResponseDto showMemberDto = ShowMemberResponseDto.entityToDto(showMember);
        ShowMemberResponseVo responseVo = ShowMemberResponseVo.dtoToVo(showMemberDto);
        return responseVo;
    }

    // Member 수정 메서드
    @Override
    @Transactional
    public void update(UUID uuid , UpdateMemberRequestDto updateMemberRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));

        Member updatedMember = Member.builder()
            .memberId(member.getMemberId())
            .uuid(member.getUuid())
            .email(member.getEmail())
            .password(member.getPassword())
            .name(updateMemberRequestDto.getName() != null ? updateMemberRequestDto.getName() : member.getName()) // 업데이트 값 적용
            .birth(updateMemberRequestDto.getBirth() != null ? updateMemberRequestDto.getBirth() : member.getBirth())
            .phone(updateMemberRequestDto.getPhone() != null ? updateMemberRequestDto.getPhone() : member.getPhone())
            .studentId(member.getStudentId())
            .manageApprovalStatus(member.isManageApprovalStatus())
            .role(member.getRole())
            .build();

        memberRepository.save(updatedMember);
    }
}
