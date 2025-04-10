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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "member-controller", description = "회원관리시스템")
public class MemberController {

    private final MemberService memberService;
    private final PasswordResetService passwordResetService;
    private final MemberRepository memberRepository;


    // 회원 입력
    @PostMapping
    @Operation(summary = "회원 등록", description = "회원 정보를 등록합니다.")
    public CommonResponse<MemberReadResponseVo> save(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "회원 등록 정보")
        @RequestBody MemberRegistrationRequestDto memberRegistrationRequestDto) {
        memberService.save(memberRegistrationRequestDto);
        return CommonResponse.success("Member Showed successfully");
    }

    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    @Operation(summary = "회원 등록 (엑셀 삽입)", description = "엑셀에 등록된 회원 정보를 통해 회원을 등록합니다.")
    public CommonResponse<ExcelAddMemberResponseVo> excelAddMember(
        @RequestPart("file")
        @io.swagger.v3.oas.annotations.Parameter(
            description = "회원 정보가 삽입된 엑셀 파일",
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

    @GetMapping("/{uuid}")
    @Operation(summary = "개인 정보 조회", description = "uuid를 기준으로 해당하는 회원의 정보를 조회합니다.")
    public CommonResponse<MemberReadResponseVo> showMember(
        @Parameter(description = "회원의 고유 번호")
        @PathVariable UUID uuid) {
        MemberReadResponseDto showMemberDto = memberService.show(uuid);
        MemberReadResponseVo responseVo = MemberReadResponseVo.dtoToVo(showMemberDto);
        return CommonResponse.success("해당하는 회원의 정보를 조회하는데 성공하였습니다.", responseVo);
    }


    @PatchMapping("/{uuid}")
    @Operation(summary = "개인 정보 수정", description = "uuid를 기준으로 해당하는 회원의 정보를 수정합니다.")
    public CommonResponse<Void> updateMember(
        @Parameter(description = "회원의 고유 번호")
        @PathVariable UUID uuid,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "회원 수정 정보")
        @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.update(uuid, memberUpdateRequestDto);

        return CommonResponse.success("회원 정보 수정에 성공하였습니다.");
    }

    @GetMapping
    @Operation(summary = "회원 목록 조회", description = "페이지 번호, 사이즈, 검색어를 기준으로 회원 목록을 조회합니다.")
    @Parameters({
        @Parameter(name = "page", description = "페이지 번호, 기본 값: 0"),
        @Parameter(name = "size", description = "페이지 당 컨텐츠 개수, 기본 값: 10"),
        @Parameter(name = "search_name", description = "검색할 회원의 이름, 기본 값: null (선택)")
    })
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
    @Operation(summary = "회원 활동 가능 여부 및 역할 변경", description = "uuid를 기준으로 회원의 활동 가능 여부 및 역할을 변경합니다.")
    public CommonResponse<String> deactivateMember(
        @Parameter(description = "회원의 고유 번호")
        @PathVariable UUID uuid,

        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "변경 정보")
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
        return CommonResponse.success("회원의 활동 가능 여부 및 역할이 변경되었습니다.",
            "deactivate-at : " + ZonedDateTime.now());
    }

}
