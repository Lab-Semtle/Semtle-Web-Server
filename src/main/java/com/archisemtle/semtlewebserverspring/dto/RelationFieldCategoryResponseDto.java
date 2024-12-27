package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RelationFieldCategoryResponseDto {

    private Long RelationFieldCategoryId;
    private String RelationFieldCategoryName;

    public static RelationFieldCategoryResponseDto entityToDto(
        RelationFieldCategory relationFieldCategory) {
        return RelationFieldCategoryResponseDto.builder()
            .RelationFieldCategoryId(relationFieldCategory.getId())
            .RelationFieldCategoryName(relationFieldCategory.getName())
            .build();
    }
}
