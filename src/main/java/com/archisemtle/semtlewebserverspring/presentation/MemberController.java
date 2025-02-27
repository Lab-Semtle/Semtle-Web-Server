package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.member.MemberService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberUpdateRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.vo.member.LoginResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.MemberReadResponseVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // 회원 입력
    @PostMapping("/members")
    public CommonResponse<MemberReadResponseVo> save(@RequestBody MemberRegistrationRequestDto memberRegistrationRequestDto) {
        memberService.save(memberRegistrationRequestDto);
        return CommonResponse.success("Member Showed successfully");
    }

    // 개인 정보 조회
    // 입력 - uuid
    // 출력 - 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @GetMapping("/members/{uuid}")
    public CommonResponse<MemberReadResponseVo> showMember(@PathVariable UUID uuid){
        MemberReadResponseDto showMemberDto =  memberService.show(uuid);
        MemberReadResponseVo responseVo = MemberReadResponseVo.dtoToVo(showMemberDto);
        return CommonResponse.success("해당하는 멤버의 정보를 조회하는데 성공하였습니다.", responseVo);
    }

    // 개인 정보 수정
    // uuid, 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @PatchMapping("/members/{uuid}")
    public CommonResponse<Void> updateMember(@PathVariable UUID uuid, @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.update(uuid, memberUpdateRequestDto);

        return CommonResponse.success("회원 정보 수정에 성공하였습니다.");
    }

    // 로그인
    @PostMapping("/auth/signin")
    public CommonResponse<LoginResponseVo> Login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseVo loginResponseVo = LoginResponseVo.dtoToVo(memberService.login(loginRequestDto));
        return CommonResponse.success("로그인에 성공하였습니다.", loginResponseVo);
    }
}
