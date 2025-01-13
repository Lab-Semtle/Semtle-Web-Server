package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.MemberService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.UpdateMember;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.vo.ProjectTypeCategoryResponseVo;
import com.archisemtle.semtlewebserverspring.vo.UpdateMemberResponseVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원 입력
    @PostMapping("/api/v1/members")
    public Member save(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.save(memberRequestDto);
        return null;
    }

    // 개인 정보 조회
    // 입력 - uuid
    // 출력 - 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @GetMapping("/api/v1/members/{uuid}")
    public Member showMember(@PathVariable UUID uuid){
        Member member = memberService.show(uuid);
        return member;
    }

    // 개인 정보 수정
    // uuid, 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @PatchMapping("/api/v1/members/{uuid}")
    public CommonResponse<UpdateMemberResponseVo> updateMember(@PathVariable UUID uuid, @RequestBody UpdateMemberRequestDto updateMemberRequestDto) {
        // memberService.update(uuid, updateMemberRequestDto);

        UpdateMemberResponseDto updatedMemberDto = memberService.update(uuid,
            updateMemberRequestDto);

        UpdateMemberResponseVo responseVo = UpdateMemberResponseVo.dtoToVo(updatedMemberDto);

        return CommonResponse.success("Member updated successfully", responseVo);
    }
}
