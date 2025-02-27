package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import com.archisemtle.semtlewebserverspring.dto.RelationFieldCategoryRequestDto;
import com.archisemtle.semtlewebserverspring.dto.RelationFieldCategoryResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RelationFieldCategoryServiceImpl implements RelationFieldCategoryService {

    private final RelationFieldCategoryRepository relationFieldCategoryRepository;

    @Override
    public List<RelationFieldCategoryResponseDto> getRelationFieldCategory() {
        List<RelationFieldCategory> RelationFieldCategoryList = relationFieldCategoryRepository.findAll();
        return RelationFieldCategoryList.stream()
            .map(RelationFieldCategoryResponseDto::entityToDto).toList();
    }

    @Override
    public void addRelationFieldCategory(
        RelationFieldCategoryRequestDto relationFieldCategoryRequestDto) {
        RelationFieldCategory relationFieldCategory = RelationFieldCategory.builder()
            .name(relationFieldCategoryRequestDto.getName())
            .build();
        relationFieldCategoryRepository.save(relationFieldCategory);
    }
}
