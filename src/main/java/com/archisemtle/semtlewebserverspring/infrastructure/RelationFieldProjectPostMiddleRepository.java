package com.archisemtle.semtlewebserverspring.infrastructure;


import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationFieldProjectPostMiddleRepository extends
    JpaRepository<RelationFieldProjectPostMiddle, Long> {

    List<RelationFieldProjectPostMiddle> findAllByProjectBoardId(Long projectBoardId);
    void deleteAllByProjectBoardId(Long projectBoardId);

}