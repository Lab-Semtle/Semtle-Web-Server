package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
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
    private String projectTypeCategory;
    private List<String> relationFieldCategory;
    private Date projectStartTime;
    private Date projectEndTime;
    private Date projectRecruitingEndTime;
    private ProjectStatus projectStatus;
}
