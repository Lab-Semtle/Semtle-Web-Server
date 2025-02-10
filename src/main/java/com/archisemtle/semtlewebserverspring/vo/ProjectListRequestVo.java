package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectListRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectListRequestVo {

    private Long projectBoardId;

    public static ProjectListRequestVo dtoToVo(ProjectListRequestDto projectListRequestDto){
        return ProjectListRequestVo.builder().projectBoardId(projectListRequestDto.getProjectBoardId()).build();
    }
}
