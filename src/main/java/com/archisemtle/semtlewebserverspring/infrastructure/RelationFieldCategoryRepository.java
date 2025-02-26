package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationFieldCategoryRepository extends
    JpaRepository<RelationFieldCategory, Long> {

    @Query("SELECT DISTINCT rfc FROM RelationFieldCategory rfc WHERE rfc.name = :name")
    RelationFieldCategory findOneByName(@Param("name") String name);
}
