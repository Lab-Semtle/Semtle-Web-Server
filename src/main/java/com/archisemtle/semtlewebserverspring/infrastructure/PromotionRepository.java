package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PromotionRepository  extends JpaRepository<ProjectBoard, Long> {

    @Query("""
            SELECT DISTINCT pb FROM project_board pb 
            LEFT JOIN FETCH pb.images img 
            JOIN FETCH pb.projectTypeCategory pt 
            LEFT JOIN FETCH pb.relationFieldProjectPostMiddleList rf 
             WHERE pb.useYn='Y' AND ( COALESCE(:keyword, '') = '' OR pb.title LIKE CONCAT('%', :keyword, '%') OR pb.content LIKE CONCAT('%', :keyword, '%') )
    """)
    Page<ProjectBoard> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT pb FROM project_board pb WHERE pb.id = :id AND pb.useYn = 'Y'")
    ProjectBoard findOneById(@Param("id") Long id);

}
