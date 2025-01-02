package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, Long> {

}