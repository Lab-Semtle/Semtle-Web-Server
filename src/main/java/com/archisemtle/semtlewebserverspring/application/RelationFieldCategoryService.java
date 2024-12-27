package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.RelationFieldCategoryRequestDto;
import com.archisemtle.semtlewebserverspring.dto.RelationFieldCategoryResponseDto;
import java.util.List;

public interface RelationFieldCategoryService {

    List<RelationFieldCategoryResponseDto> getRelationFieldCategory();

    void addRelationFieldCategory(RelationFieldCategoryRequestDto relationFieldCategoryRequestDto);
}
