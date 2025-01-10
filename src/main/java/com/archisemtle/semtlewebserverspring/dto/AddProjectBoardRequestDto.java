package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddProjectBoardRequestDto {

    private String title;
    private String content;
    private String writerUuid;
    private String writerName;
    private ProjectTypeCategory projectTypeCategory;
    private List<RelationFieldCategory> relationFieldCategories;
    private Date projectStartTime;
    private Date projectEndTime;
    private Date projectRecruitingEndTime;
}
