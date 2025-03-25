package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.vo.ProjectBoardPageResponseVo;
import com.archisemtle.semtlewebserverspring.vo.ProjectBoardResponseVo;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectBoardPageResponseDto {

    private String title;
    private String writerName;
    private String projectTypeCategoryName;
    private List<String> relationFieldCategoryName;
    private LocalDate projectRecruitingEndTime;
    private ProjectStatus projectStatus;

    public static ProjectBoardPageResponseVo dtoToVo(ProjectBoardPageResponseDto projectBoardPageResponseDto) {
        return ProjectBoardPageResponseVo.builder()
            .title(projectBoardPageResponseDto.getTitle())
            .writerName(projectBoardPageResponseDto.getWriterName())
            .projectTypeCategory(projectBoardPageResponseDto.getProjectTypeCategoryName())
            .relationFieldCategory(projectBoardPageResponseDto.getRelationFieldCategoryName())
            .projectRecruitingEndTime(projectBoardPageResponseDto.getProjectRecruitingEndTime())
            .projectStatus(projectBoardPageResponseDto.getProjectStatus())
            .build();
    }
}
