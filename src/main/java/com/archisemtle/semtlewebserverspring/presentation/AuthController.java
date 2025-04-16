package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.member.MemberService;
import com.archisemtle.semtlewebserverspring.application.member.PasswordResetService;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.verifyAdminRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.vo.member.LoginResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.MemberPasswordResetResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "auth-controller", description = "보안")
public class AuthController {

    private final MemberService memberService;
    private final PasswordResetService passwordResetService;
    private final MemberRepository memberRepository;

    // 로그인
    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    public CommonResponse<LoginResponseVo> Login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "로그인 정보")
        @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseVo loginResponseVo = LoginResponseVo.dtoToVo(
            memberService.login(loginRequestDto));
        return CommonResponse.success("로그인에 성공하였습니다.", loginResponseVo);
    }

    @PostMapping("/password/reset/email")
    @Operation(summary = "비밀번호 재설정 이메일 전송", description = "이메일을 기준으로 비밀번호 재설정 이메일을 전송합니다.")
    public CommonResponse<MemberPasswordResetResponseVo> requestPasswordReset(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "이메일")
        @RequestBody MemberPasswordResetEmailRequestDto memberPasswordResetEmailRequestDto
    ) {
        MemberPasswordResetEmailResponseDto memberPasswordResetEmailResponseDto = passwordResetService.sendPasswordResetEmail(
            memberPasswordResetEmailRequestDto.getEmail());
        MemberPasswordResetResponseVo memberPasswordResetResponseVo = MemberPasswordResetResponseVo.dtoToVo(
            memberPasswordResetEmailResponseDto);
        return CommonResponse.success("비밀번호 재설정 이메일이 성공적으로 발송되었습니다.",
            memberPasswordResetResponseVo);
    }

    @PostMapping("/password/reset")
    @Operation(summary = "비밀번호 재설정", description = "이메일을 기준으로 비밀번호 재설정 이메일을 전송합니다.")
    public CommonResponse<String> confirmPasswordReset(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "비밀번호 재설정 정보")
        @RequestBody MemberPasswordResetRequestDto memberPasswordResetRequestDto
    ) {
        passwordResetService.resetPassword(memberPasswordResetRequestDto.getToken(),
            memberPasswordResetRequestDto.getCurrentPassword(),
            memberPasswordResetRequestDto.getNewPassword(),
            memberPasswordResetRequestDto.getConfirmNewPassword());
        return CommonResponse.success("비밀번호가 성공적으로 변경되었습니다.", null);
    }

    @PutMapping("/password/manager")
    @Operation(summary = "관리자 2차 인증", description = "비밀번호를 기준으로 2차인증을 진행합니다.")
    public CommonResponse<String> verifyAdmin(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "2차 인증 정보")
        @RequestBody verifyAdminRequestDto verifyAdminRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID uuid = UUID.fromString(authentication.getName());
        Member member = memberRepository.findByUuid(uuid).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        if (!"ADMIN".equals(member.getRole())) {
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);
        }

        boolean isVerified = memberService.verifyAdmin(uuid, verifyAdminRequestDto);

        if (!isVerified) {
            return CommonResponse.fail(BaseResponseStatus.INVALID_PASSWORD, "비밀번호가 올바르지 않습니다.");
        }

        return CommonResponse.success("관리자 2차인증에 성공하셨습니다.");
    }
}
