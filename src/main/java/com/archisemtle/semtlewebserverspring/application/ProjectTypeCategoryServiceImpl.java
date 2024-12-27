package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.dto.ProjectTypeCategoryRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectTypeCategoryResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectTypeCategoryRepository;
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
public class ProjectTypeCategoryServiceImpl implements ProjectTypeCategoryService {

    private final ProjectTypeCategoryRepository projectTypeCategoryRepository;

    @Override
    public List<ProjectTypeCategoryResponseDto> getProjectTypeCategory() {
        List<ProjectTypeCategory> projectTypeCategoryList = projectTypeCategoryRepository.findAll();
        return projectTypeCategoryList.stream()
            .map(ProjectTypeCategoryResponseDto::entityToDto).toList();
    }

    @Override
    public void addProjectTypeCategory(
        ProjectTypeCategoryRequestDto projectTypeCategoryRequestDto) {
        ProjectTypeCategory projectTypeCategory = ProjectTypeCategory.builder()
            .name(projectTypeCategoryRequestDto.getName())
            .build();
        projectTypeCategoryRepository.save(projectTypeCategory);
    }
}
