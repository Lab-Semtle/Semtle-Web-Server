package com.archisemtle.semtlewebserverspring.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBoardListDto {

    private Long ProjectBoardId;
    private String title;
    private String writerName;
    private String projectTypeCategoryName;
    private List<String> relationFieldCategoryName;
    private Date projectRecruitingEndTime;
    private String projectBoardImage;

    @QueryProjection
    public ProjectBoardListDto(Long projectBoardId, String title, String writerName,
        String projectTypeCategoryName, List<String> relationFieldCategoryName,
        Date projectRecruitingEndTime, String projectBoardImage) {
        this.ProjectBoardId = projectBoardId;
        this.title = title;
        this.writerName = writerName;
        this.projectTypeCategoryName = projectTypeCategoryName;
        this.relationFieldCategoryName = relationFieldCategoryName;
        this.projectRecruitingEndTime = projectRecruitingEndTime;
        this.projectBoardImage = projectBoardImage;
    }
}
