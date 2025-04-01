package com.archisemtle.semtlewebserverspring.presentation;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.INTERNAL_SERVER_ERROR;

import com.archisemtle.semtlewebserverspring.application.member.MemberService;
import com.archisemtle.semtlewebserverspring.application.member.PasswordResetService;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.ExcelAddMemberResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberDeactiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetEmailResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberPasswordResetRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberUpdateRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.verifyAdminRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.vo.member.ExcelAddMemberResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.LoginResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.MemberListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.MemberPasswordResetResponseVo;
import com.archisemtle.semtlewebserverspring.vo.member.MemberReadResponseVo;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordResetService passwordResetService;
    private final MemberRepository memberRepository;


    // 회원 입력
    @PostMapping
    public CommonResponse<MemberReadResponseVo> save(
        @RequestBody MemberRegistrationRequestDto memberRegistrationRequestDto) {
        memberService.save(memberRegistrationRequestDto);
        return CommonResponse.success("Member Showed successfully");
    }

    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public CommonResponse<ExcelAddMemberResponseVo> excelAddMember(
        @RequestPart("file")
        @io.swagger.v3.oas.annotations.Parameter(
            required = true
        ) MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID uuid = UUID.fromString(authentication.getName());
        Member member = memberRepository.findByUuid(uuid).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        if (!"ADMIN".equals(member.getRole())) {
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);
        }

        try {
            ExcelAddMemberResponseDto responseDto = memberService.excelAddMember(file);
            ExcelAddMemberResponseVo responseVo = ExcelAddMemberResponseVo.dtoToVo(responseDto);
            return CommonResponse.success("회원들이 성공적으로 등록되었습니다.", responseVo);
        } catch (IOException e) {
            return CommonResponse.fail(INTERNAL_SERVER_ERROR, "파일 처리 중 오류가 발생했습니다.");
        }
    }

    // 개인 정보 조회
    // 입력 - uuid
    // 출력 - 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @GetMapping("/{uuid}")
    public CommonResponse<MemberReadResponseVo> showMember(@PathVariable UUID uuid) {
        try{
            MemberReadResponseDto showMemberDto = memberService.show(uuid);
            MemberReadResponseVo responseVo = MemberReadResponseVo.dtoToVo(showMemberDto);
            return CommonResponse.success("해당하는 멤버의 정보를 조회하는데 성공하였습니다.", responseVo);
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    // 개인 정보 수정
    // 이름, 생년월일, 전화번호, 프로필 사진
    @PatchMapping("/{uuid}")
    public CommonResponse<Integer> updateMember(@PathVariable UUID uuid,
        @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        try {
            memberService.update(uuid, memberUpdateRequestDto);
            return CommonResponse.success(BaseResponseStatus.SUCCESS.getMessage(), BaseResponseStatus.SUCCESS.getCode());
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }

    }

    @GetMapping
    public CommonResponse<MemberListResponseVo> getMemberList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "search_name", required = false) String searchName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID uuid = UUID.fromString(authentication.getName());
        Member member = memberRepository.findByUuid(uuid).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        if (!"ADMIN".equals(member.getRole())) {
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);
        }

        Page<MemberListResponseDto> memberPage = memberService.getMemberList(page, size,
            searchName);

        if (memberPage.isEmpty()) {
            // 검색 결과가 없을 경우 빈 결과 반환
            return CommonResponse.success("회원 리스트", MemberListResponseVo.builder()
                .totalMembers(0)
                .currentPage(page + 1)
                .totalPages(0)
                .members(List.of())
                .build());
        }

        return CommonResponse.success("회원 리스트",
            MemberListResponseVo.dtoToVo(memberPage.getContent().get(0)));
    }

    @PatchMapping("/{uuid}/deactivate")
    public CommonResponse<String> deactivateMember(
        @PathVariable UUID uuid,
        @RequestBody MemberDeactiveRequestDto memberDeactiveRequestDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userUuid = UUID.fromString(authentication.getName());
        Member member = memberRepository.findByUuid(userUuid).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        if (!"ADMIN".equals(member.getRole())) {
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);
        }

        memberService.deactivateMember(uuid, memberDeactiveRequestDto);
        return CommonResponse.success("회원의 활동 가능 여부가 변경되었습니다.",
            "deactivate-at : " + ZonedDateTime.now());
    }

}
