package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoardImage;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBoardResponseVo {

    private String title;
    private String content;
    private String writerName;
    private String contact;
    private String projectTypeCategory;
    private List<String> relationFieldCategory;
    private LocalDate projectStartTime;
    private LocalDate projectEndTime;
    private LocalDate projectRecruitingEndTime;
    private ProjectStatus projectStatus;
    private List<ProjectBoardImage> projectBoardImages;
}
