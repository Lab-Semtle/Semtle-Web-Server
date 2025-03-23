package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddProjectBoardRequestDto {

    private String title;
    private String content;
    private String contact;
    private ProjectTypeCategory projectTypeCategory;
    private List<RelationFieldCategory> relationFieldCategories;
    private Date projectStartTime;
    private Date projectEndTime;
    private Date projectRecruitingEndTime;
    private List<String> projectBoardImages;
}
