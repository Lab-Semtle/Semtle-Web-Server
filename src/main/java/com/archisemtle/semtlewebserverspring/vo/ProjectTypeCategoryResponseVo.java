package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectTypeCategoryResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ProjectTypeCategoryResponseVo {

    private Long projectTypeCategoryId;
    private String ProjectTypeCategoryName;

    public static ProjectTypeCategoryResponseVo dtoToVo(
        ProjectTypeCategoryResponseDto projectTypeCategoryResponseDto) {
        return ProjectTypeCategoryResponseVo.builder()
            .projectTypeCategoryId(projectTypeCategoryResponseDto.getProjectTypeCategoryId())
            .ProjectTypeCategoryName(projectTypeCategoryResponseDto.getProjectTypeCategoryName())
            .build();
    }
}
