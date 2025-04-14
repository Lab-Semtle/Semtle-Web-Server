package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoardImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectBoardImageRepository extends JpaRepository<ProjectBoardImage, Long> {

    List<ProjectBoardImage> findAllByProjectBoardId(Long id);
    void deleteAllByProjectBoardId(Long id);
}
