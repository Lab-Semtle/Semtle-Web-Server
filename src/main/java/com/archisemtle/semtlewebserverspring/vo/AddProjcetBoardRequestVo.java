package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import com.archisemtle.semtlewebserverspring.dto.AddProjectBoardRequestDto;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AddProjcetBoardRequestVo {

    private String title;
    private String content;
    private String contact;
    private ProjectTypeCategory projectTypeCategory;
    private List<RelationFieldCategory> relationFieldCategories;
    private LocalDate projectStartTime;
    private LocalDate projectEndTime;
    private LocalDate projectRecruitingEndTime;
    private List<String> projectBoardImages;

    public static AddProjectBoardRequestDto voToDto(
        AddProjcetBoardRequestVo addProjcetBoardRequestVo) {
        return AddProjectBoardRequestDto.builder()
            .title(addProjcetBoardRequestVo.getTitle())
            .content(addProjcetBoardRequestVo.getContent())
            .contact(addProjcetBoardRequestVo.getContact())
            .projectTypeCategory(addProjcetBoardRequestVo.getProjectTypeCategory())
            .relationFieldCategories(addProjcetBoardRequestVo.getRelationFieldCategories())
            .projectStartTime(addProjcetBoardRequestVo.getProjectStartTime())
            .projectEndTime(addProjcetBoardRequestVo.getProjectEndTime())
            .projectRecruitingEndTime(addProjcetBoardRequestVo.getProjectRecruitingEndTime())
            .projectBoardImages(addProjcetBoardRequestVo.getProjectBoardImages())
            .build();
    }
}
