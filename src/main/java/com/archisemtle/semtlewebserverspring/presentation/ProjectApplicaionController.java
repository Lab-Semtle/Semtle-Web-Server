package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ProjectApplicationService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import com.archisemtle.semtlewebserverspring.vo.ChangeApplyStatusResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ShowApplyingProjectListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ShowProjectApplicantInfoResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ShowProjectApplicantsListResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/own/projects")
public class ProjectApplicaionController {

    private final ProjectApplicationService projectApplicationService;

    // 본인이 게시한 프로젝트(공고) 신청자 목록 조회 API
    @GetMapping("/{post_id}/applicants")
    @Operation(summary = "자신이 신청한 프로젝트 목록을 조회하는 API", description = "자신이 신청한 프로젝트목록을 조회하는 API입니다.")
    public CommonResponse<ShowProjectApplicantsListResponseVo> ShowProjectApplicantsList(
        @PathVariable("post_id") Integer postId,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit) throws Exception {

        ProjectApplicantsResponseDto projectApplicantsResponseDto = projectApplicationService.getApplicants(
            postId, page, limit);
        ShowProjectApplicantsListResponseVo showProjectApplicantsResponseVo = ShowProjectApplicantsListResponseVo.dtoToVo(
            projectApplicantsResponseDto);

        return CommonResponse.success("applicants Showed successfully",
            showProjectApplicantsResponseVo);
    }

    // 본인이 게시한 프로젝트(공고) 신청자 정보(내용) 조회 API
    @GetMapping("/{post_id}/applicants/{id}")
    @Operation(summary = "자신이 모집한 프로젝트에서 신청자 정보를 조회하는 API", description = "자신이 모집한 프로젝트에서 신청자 정보를 조회하는 API입니다.")
    public CommonResponse<ShowProjectApplicantInfoResponseVo> ShowProjectApplicantsInfo(
        @PathVariable("post_id") Integer boardId, @PathVariable("id") Integer applicantId)
        throws Exception {

        ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto = projectApplicationService.getApplicantInfo(
            boardId, applicantId);

        ShowProjectApplicantInfoResponseVo showProjectApplicantInfoResponseVo = ShowProjectApplicantInfoResponseVo.dtoToVo(
            showProjectApplicantInfoResponseDto);
        return CommonResponse.success("applicants Info Showed successfully",
            showProjectApplicantInfoResponseVo);
    }

    // 본인이 게시한 프로젝트(공고) 신청자 승인 상태 변경 API
    @PatchMapping("/{post_id}/applicants/{id}")
    @Operation(summary = "자신이 모집한 프로젝트에서 신청자 승인 상태 변경하는 API", description = "자신이 모집한 프로젝트에서 신청자 승인 상태 변경하는 API입니다.")
    public CommonResponse<ChangeApplyStatusResponseVo> ChangeApplyStatus(
        @PathVariable("post_id") Integer boardId,
        @PathVariable("id") Integer applicantId,
        @RequestBody ChangeApplyStatusRequestDto changeApplyStatusRequestDto
    ) throws Exception {

        ChangeApplyStatusResponseDto changeApplyStatusResponseDto = projectApplicationService.changeApplyStatus(
            boardId, applicantId, changeApplyStatusRequestDto.getStatus());

        ChangeApplyStatusResponseVo changeApplyStatusResponseVo = ChangeApplyStatusResponseVo.dtoToVo(
            changeApplyStatusResponseDto);
        return CommonResponse.success("applicant Status changed successfully",
            changeApplyStatusResponseVo);
    }

    // 본인이 신청한 프로젝트 목록 조회 (승인결과포함) API
    @GetMapping("/applications/{id}")
    @Operation(summary = "자신이 신청한 프로젝트 목록 조회하는 API", description = "자신이 신청한 프로젝트 목록 조회하는 API입니다.")
    public CommonResponse<ShowApplyingProjectListResponseVo> ShowApplyingProjectList(
        @PathVariable("id") Integer applicantId,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit) throws Exception {

        ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto = projectApplicationService.getApplyingProjectInfo(
            applicantId, page, limit);

        ShowApplyingProjectListResponseVo showApplyingProjectListResponseVo = ShowApplyingProjectListResponseVo.dtoToVo(
            showApplyingProjectInfoResponseDto);
        return CommonResponse.success("Applying applications Showed successfully",
            showApplyingProjectListResponseVo);
    }
}
