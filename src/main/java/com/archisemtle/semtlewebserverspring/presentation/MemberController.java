package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.member.MemberService;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberDeactiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberUpdateRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.vo.member.LoginResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.MemberReadResponseVo;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PatchMapping("/{uuid}/deactivate")
    public CommonResponse<String> deactivateMember(
        @PathVariable UUID uuid,
        @RequestBody MemberDeactiveRequestDto memberDeactiveRequestDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userUuid = UUID.fromString(authentication.getName());
        Member member = memberRepository.findByUuid(userUuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        if (!"ADMIN".equals(member.getRole())) {
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);
        }

        memberService.deactivateMember(uuid, memberDeactiveRequestDto);
        return CommonResponse.success("회원의 활동 가능 여부가 변경되었습니다.", "deactivate-at : " + ZonedDateTime.now());
    }

    // 로그인
    @PostMapping("/auth/signin")
    public CommonResponse<LoginResponseVo> Login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseVo loginResponseVo = LoginResponseVo.dtoToVo(memberService.login(loginRequestDto));
        return CommonResponse.success("로그인에 성공하였습니다.", loginResponseVo);
    }
}
