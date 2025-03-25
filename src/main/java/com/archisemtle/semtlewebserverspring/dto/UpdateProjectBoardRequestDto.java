package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProjectBoardRequestDto {
    private String title;
    private String content;
    private String contact;
    private ProjectTypeCategory projectTypeCategory;
    private List<RelationFieldCategory> relationFieldCategories;
    private LocalDate projectStartTime;
    private LocalDate projectEndTime;
    private LocalDate projectRecruitingEndTime;
}
