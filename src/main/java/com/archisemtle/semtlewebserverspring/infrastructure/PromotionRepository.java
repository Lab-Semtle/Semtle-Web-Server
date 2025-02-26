package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectPromotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PromotionRepository  extends JpaRepository<ProjectPromotion, Long> {

    @Query("""
            SELECT DISTINCT pp FROM project_promotion pp
            LEFT JOIN FETCH pp.images img 
            JOIN FETCH pp.projectTypeCategory pt 
            LEFT JOIN FETCH pp.relationFieldProjectPostMiddleList rf 
             WHERE pp.useYn='Y' AND ( COALESCE(:keyword, '') = '' OR pp.title LIKE CONCAT('%', :keyword, '%') OR pp.content LIKE CONCAT('%', :keyword, '%') )
    """)
    Page<ProjectPromotion> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT pp FROM project_promotion pp WHERE pp.id = :id AND pp.useYn = 'Y'")
    ProjectPromotion findOneById(@Param("id") Long id);

}
