package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.ProjectPromotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends JpaRepository<ProjectPromotion, Long> {
    @Query("""
            SELECT pp FROM project_promotion pp
            WHERE pp.useYn='Y' AND pp.writerUuid = :userID
            """)
    Page<ProjectPromotion> findByOwnerId(@Param("userID") String userID, Pageable pageable);
}
