package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeCategoryRepository extends JpaRepository<ProjectTypeCategory, Long> {

}
