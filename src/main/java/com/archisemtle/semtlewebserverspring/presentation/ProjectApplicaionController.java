package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ProjectApplicationService;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import com.archisemtle.semtlewebserverspring.vo.ApplyProjectResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ChangeApplyStatusResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ShowApplyingProjectListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ShowProjectApplicantInfoResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ShowProjectApplicantsListResponseVo;
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
    public CommonResponse<ShowProjectApplicantsListResponseVo> ShowProjectApplicantsList(
        @PathVariable("post_id") Long postId,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit){
        try {
            ProjectApplicantsResponseDto projectApplicantsResponseDto = projectApplicationService.getApplicants(postId, page, limit);
            ShowProjectApplicantsListResponseVo showProjectApplicantsResponseVo = ShowProjectApplicantsListResponseVo.dtoToVo(projectApplicantsResponseDto);
            return CommonResponse.success(BaseResponseStatus.SUCCESS.getMessage(), showProjectApplicantsResponseVo);
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    // 본인이 게시한 프로젝트(공고) 신청자 정보(내용) 조회 API
    @GetMapping("/{post_id}/applicants/{applicant_id}")
    public CommonResponse<ShowProjectApplicantInfoResponseVo> ShowProjectApplicantsInfo(@PathVariable("post_id") Long postId, @PathVariable("applicant_id") Long applicantId) {
        try {
            ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto = projectApplicationService.getApplicantInfo(postId, applicantId);
            ShowProjectApplicantInfoResponseVo showProjectApplicantInfoResponseVo = ShowProjectApplicantInfoResponseVo.dtoToVo(showProjectApplicantInfoResponseDto);
            return CommonResponse.success(BaseResponseStatus.SUCCESS.getMessage(), showProjectApplicantInfoResponseVo);
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    // 본인이 게시한 프로젝트(공고) 신청자 승인 상태 변경 API
    @PatchMapping("/{post_id}/applicants/{applicant_id}")
    public CommonResponse<ChangeApplyStatusResponseVo> ChangeApplyStatus(
        @PathVariable("post_id") Long postId,
        @PathVariable("applicant_id") Long applicantId,
        @RequestBody ChangeApplyStatusRequestDto changeApplyStatusRequestDto
        ) {
        try {
            ChangeApplyStatusResponseDto changeApplyStatusResponseDto = projectApplicationService.changeApplyStatus(postId,applicantId,changeApplyStatusRequestDto.getStatus());
            ChangeApplyStatusResponseVo changeApplyStatusResponseVo = ChangeApplyStatusResponseVo.dtoToVo(changeApplyStatusResponseDto);
            return CommonResponse.success(BaseResponseStatus.SUCCESS.getMessage(), changeApplyStatusResponseVo);
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    // 본인이 신청한 프로젝트 목록 조회 (승인결과포함) API
    @GetMapping("/applications/{member_id}")
    public CommonResponse<ShowApplyingProjectListResponseVo> ShowApplyingProjectList(
        @PathVariable("member_id") int memberId,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit) {
        try {
            ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto = projectApplicationService.getApplyingProjectInfo(memberId, page, limit);
            ShowApplyingProjectListResponseVo showApplyingProjectListResponseVo = ShowApplyingProjectListResponseVo.dtoToVo(showApplyingProjectInfoResponseDto);
            return CommonResponse.success(BaseResponseStatus.SUCCESS.getMessage(), showApplyingProjectListResponseVo);
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
