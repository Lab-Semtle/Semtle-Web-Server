package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import com.archisemtle.semtlewebserverspring.dto.UpdateProjectBoardRequestDto;
import java.util.Date;
import java.util.List;
import lombok.Getter;

@Getter
public class UpdateProjectBoardRequestVo {
    private String title;
    private String content;
    private ProjectTypeCategory projectTypeCategory;
    private List<RelationFieldCategory> relationFieldCategories;
    private Date projectStartTime;
    private Date projectEndTime;
    private Date projectRecruitingEndTime;

    public static UpdateProjectBoardRequestDto voToDto(
        UpdateProjectBoardRequestVo updateProjectBoardRequestVo) {
        return UpdateProjectBoardRequestDto.builder()
            .title(updateProjectBoardRequestVo.getTitle())
            .content(updateProjectBoardRequestVo.getContent())
            .projectTypeCategory(updateProjectBoardRequestVo.getProjectTypeCategory())
            .relationFieldCategories(updateProjectBoardRequestVo.getRelationFieldCategories())
            .projectStartTime(updateProjectBoardRequestVo.getProjectStartTime())
            .projectEndTime(updateProjectBoardRequestVo.getProjectEndTime())
            .projectRecruitingEndTime(updateProjectBoardRequestVo.getProjectRecruitingEndTime())
            .build();
    }
}
