package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ApplyProjectService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.vo.ApplyProjectResponseVo;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/{post_id}/apply/{id}")
    @Operation(summary = "공고 신청API", description = "공고에 사용자가 신성합니다.")
    public CommonResponse<ApplyProjectResponseVo> ApplyProject(
        @PathVariable("post_id") Integer boardId,
        @PathVariable("id") Integer applicantId,
        @RequestBody ApplyProjectRequestDto applyProjectRequestDto
    ) throws Exception {
        ApplyProjectResponseDto applyProjectResponseDto = applyProjectService.applyProject(boardId, applicantId, applyProjectRequestDto);
        ApplyProjectResponseVo applyProjectResponseVo = ApplyProjectResponseVo.dtoToVo(applyProjectResponseDto);

        if(applyProjectResponseVo.getAppliedAt() != null) {
            return CommonResponse.success("applied successfully", applyProjectResponseVo);
        }else{
            return CommonResponse.fail(null,"apply failed");
        }
    }
}
