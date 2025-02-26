package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, Long>,
    ProjectBoardRepositoryCustom {

    @Override
    Optional<ProjectBoard> findById(Long id);

    @Query("""
        SELECT pb FROM project_board pb ORDER BY CASE pb.projectStatus WHEN 'RECRUITING' THEN 1 WHEN 'IN_PROGRESS' THEN 2 WHEN 'CLOSED' THEN 3 END ASC
        """)
    Page<ProjectBoard> findAllOrderByCustomStatus(Pageable pageable);

    List<ProjectBoard> findAllByProjectStatus(ProjectStatus projectStatus);
}