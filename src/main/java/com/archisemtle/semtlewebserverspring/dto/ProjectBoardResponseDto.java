package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoardImage;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
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
public class ProjectBoardResponseDto {

    private String title;
    private String content;
    private String writerName;
    private String contact;
    private String projectTypeCategoryName;
    private List<String> relationFieldCategoryName;
    private LocalDate projectStartTime;
    private LocalDate projectEndTime;
    private LocalDate projectRecruitingEndTime;
    private ProjectStatus projectStatus;
    private List<String> projectBoardImages;

    public static ProjectBoardResponseVo dtoToVo(ProjectBoardResponseDto projectBoardResponseDto) {
        return ProjectBoardResponseVo.builder()
            .title(projectBoardResponseDto.getTitle())
            .content(projectBoardResponseDto.getContent())
            .writerName(projectBoardResponseDto.getWriterName())
            .contact(projectBoardResponseDto.getContact())
            .projectTypeCategory(projectBoardResponseDto.getProjectTypeCategoryName())
            .relationFieldCategory(projectBoardResponseDto.getRelationFieldCategoryName())
            .projectStartTime(projectBoardResponseDto.getProjectStartTime())
            .projectEndTime(projectBoardResponseDto.getProjectEndTime())
            .projectRecruitingEndTime(projectBoardResponseDto.getProjectRecruitingEndTime())
            .projectStatus(projectBoardResponseDto.getProjectStatus())
            .projectBoardImages(projectBoardResponseDto.getProjectBoardImages())
            .build();
    }
}
