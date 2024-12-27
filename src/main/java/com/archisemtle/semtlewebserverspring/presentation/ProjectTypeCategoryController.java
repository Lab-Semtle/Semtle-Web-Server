package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ProjectTypeCategoryService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.vo.ProjectTypeCategoryResponseVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projectTypeCategory")
public class ProjectTypeCategoryController {

    private final ProjectTypeCategoryService projectTypeCategoryService;

    @GetMapping("/categorylist")
    public CommonResponse<List<ProjectTypeCategoryResponseVo>> ProjectTypeCategoryList() {
        return CommonResponse.success("",
            projectTypeCategoryService.getProjectTypeCategory().stream()
                .map(ProjectTypeCategoryResponseVo::dtoToVo).toList());
    }
}
