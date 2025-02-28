package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationFieldProjectPostMiddleRepository extends
    JpaRepository<RelationFieldProjectPostMiddle, Long> {

    List<RelationFieldProjectPostMiddle> findAllByProjectBoardId(Long projectBoardId);
    void deleteAllByProjectBoardId(Long projectBoardId);

}