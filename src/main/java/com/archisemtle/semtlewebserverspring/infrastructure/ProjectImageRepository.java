package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.ProjectImage;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    Optional<ProjectImage> findByProjectBoardAndId(ProjectBoard projectBoard, Long id);

    ProjectImage findOneByProjectBoard(ProjectBoard projectBoard);
}
