package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBoardPageResponseVo {

    private String title;
    private String writerName;
    private String projectTypeCategory;
    private List<String> relationFieldCategory;
    private LocalDate projectRecruitingEndTime;
    private ProjectStatus projectStatus;
}
