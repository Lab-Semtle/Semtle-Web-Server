package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ProjectTypeCategoryService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.vo.ProjectTypeCategoryResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projecttypecategory")
public class ProjectTypeCategoryController {

    private final ProjectTypeCategoryService projectTypeCategoryService;

    @GetMapping("/categorylist")
    @Operation(summary = "프로젝트 모집 카테고리 조회 API", description = "프로젝트 모집 카테고리 조회하는 API입니다.")
    public CommonResponse<List<ProjectTypeCategoryResponseVo>> ProjectTypeCategoryList() {
        return CommonResponse.success("",
            projectTypeCategoryService.getProjectTypeCategory().stream()
                .map(ProjectTypeCategoryResponseVo::dtoToVo).toList());
    }
}
