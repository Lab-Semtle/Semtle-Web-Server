package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ApplyProjectService;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.vo.ApplyProjectResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ApplyingController {

    private final ApplyProjectService applyProjectService;

    // 공고 신청
    @PostMapping("/{post_id}/apply/{applicant_id}")
    public CommonResponse<ApplyProjectResponseVo> ApplyProject(
        @PathVariable("post_id") Long postId,
        @PathVariable("applicant_id") Long applicantId,
        @RequestBody ApplyProjectRequestDto applyProjectRequestDto
    ){
        try {
            ApplyProjectResponseDto applyProjectResponseDto = applyProjectService.applyProject(postId, applicantId, applyProjectRequestDto);
            ApplyProjectResponseVo applyProjectResponseVo = ApplyProjectResponseVo.dtoToVo(applyProjectResponseDto);
            return CommonResponse.success(BaseResponseStatus.SUCCESS.getMessage(), applyProjectResponseVo);
        } catch (BaseException e) {
            return CommonResponse.fail(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            return CommonResponse.fail(BaseResponseStatus.INTERNAL_SERVER_ERROR, BaseResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
