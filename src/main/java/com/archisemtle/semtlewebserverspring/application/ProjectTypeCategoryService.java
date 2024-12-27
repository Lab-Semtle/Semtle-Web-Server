package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.ProjectTypeCategoryRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectTypeCategoryResponseDto;
import java.util.List;

public interface ProjectTypeCategoryService {

    List<ProjectTypeCategoryResponseDto> getProjectTypeCategory();

    void addProjectTypeCategory(ProjectTypeCategoryRequestDto projectTypeCategoryRequestDto);
}
