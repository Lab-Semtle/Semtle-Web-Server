package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.ProjectBoardService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.common.ProjectBoardSearchCondition;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardListDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardPageResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectListRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.vo.AddProjcetBoardRequestVo;
import com.archisemtle.semtlewebserverspring.vo.ProjectBoardPageResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ProjectBoardResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ProjectListRequestVo;
import com.archisemtle.semtlewebserverspring.vo.UpdateProjectBoardRequestVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projectboard")
public class ProjectBoardController {

    private final ProjectBoardService projectBoardService;
    private final ProjectBoardRepository projectBoardRepository;

    @PostMapping("/write")
    public CommonResponse<String> addProjectBoard(
        @RequestBody AddProjcetBoardRequestVo addProjcetBoardRequestVo) {
        projectBoardService.addProjectBoard(
            AddProjcetBoardRequestVo.voToDto(addProjcetBoardRequestVo));
        return CommonResponse.success("게시글 작성 성공");
    }

    @GetMapping("/{projectBoardId}")
    public CommonResponse<ProjectBoardResponseVo> getProjectBoardCommonResponse(
        @PathVariable("projectBoardId") Long id) {

        return CommonResponse.success("ok",
            ProjectBoardResponseDto.dtoToVo(projectBoardService.getProjectBoard(id)));
    }

    @DeleteMapping("/{projectBoardId}")
    public CommonResponse<String> deleteProjectBoard(@PathVariable("projectBoardId") Long id) {
        projectBoardService.deleteProjectBoard(id);
        return CommonResponse.success("게시글 삭제 성공");
    }

    @PostMapping("/update/{projectBoardId}")
    public CommonResponse<String> updateProjectBoard(@PathVariable("projectBoardId") Long id,
        @RequestBody
        UpdateProjectBoardRequestVo updateProjectBoardRequestVo) {
        projectBoardService.updateProjectBoard(id,
            UpdateProjectBoardRequestVo.voToDto(updateProjectBoardRequestVo));
        return CommonResponse.success("게시물 수정 성공");
    }

    @GetMapping("/projectboardlist")
    public CommonResponse<List<ProjectListRequestVo>> getProjectBoardList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<ProjectListRequestDto> projectBoardList = projectBoardService.getProjectBoardList(page,
            size);

        return CommonResponse.success("게시물 리스트",
            projectBoardList.stream().map(ProjectListRequestVo::dtoToVo).toList());
    }

    @GetMapping("/projectboardlist2")
    public CommonResponse<Page<ProjectBoardListDto>> getProjectBoardList2(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "project_type", required = false) String projectType,
        @RequestParam(name = "relation_type", required = false) String relationType
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ProjectBoardSearchCondition condition = ProjectBoardSearchCondition.builder()
            .ProjectTypeCategoryName(projectType)
            .relationFieldCategoryName(relationType)
            .build();
        return CommonResponse.success("게시물 리스트",
            projectBoardRepository.searchPageComplex(condition, pageable));
    }

    @GetMapping("/projectboardlist/{projectBoardId}")
    public CommonResponse<ProjectBoardPageResponseVo> getProjectBoardPageCommonResponse(
        @PathVariable("projectBoardId") Long id) {

        return CommonResponse.success("ok",
            ProjectBoardPageResponseDto.dtoToVo(projectBoardService.getProjectBoardPage(id)));
    }
}
