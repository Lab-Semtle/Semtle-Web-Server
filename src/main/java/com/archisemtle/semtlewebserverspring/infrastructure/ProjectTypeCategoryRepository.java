package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectTypeCategoryRepository extends JpaRepository<ProjectTypeCategory, Long> {

    @Query("SELECT DISTINCT ptc FROM ProjectTypeCategory ptc WHERE ptc.name = :name")
    ProjectTypeCategory getProjectTypeCategoryByName(@Param("name") String name);

}
