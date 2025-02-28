package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectTypeCategoryResponseDto {

    private Long projectTypeCategoryId;
    private String ProjectTypeCategoryName;

    public static ProjectTypeCategoryResponseDto entityToDto(
        ProjectTypeCategory projectTypeCategory) {
        return ProjectTypeCategoryResponseDto.builder()
            .projectTypeCategoryId(projectTypeCategory.getId())
            .ProjectTypeCategoryName(projectTypeCategory.getName())
            .build();
    }
}
