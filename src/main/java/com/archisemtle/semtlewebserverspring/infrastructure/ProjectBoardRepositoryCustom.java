package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.common.ProjectBoardSearchCondition;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardListDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectBoardRepositoryCustom {

    Page<ProjectBoardListDto> searchPageComplex(ProjectBoardSearchCondition condition,
        Pageable pageable);

    Page<ProjectBoardListDto> myProjectBoardPage(UUID uuid, Pageable pageable);
}
