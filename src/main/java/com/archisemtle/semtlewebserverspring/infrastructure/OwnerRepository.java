package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends JpaRepository<ProjectBoard, Long> {
    @Query("""
            SELECT pb FROM project_board pb 
            WHERE pb.useYn='Y' AND pb.writerUuid = :userID
            """)
    Page<ProjectBoard> findByOwnerId(@Param("userID") String userID, Pageable pageable);
}
