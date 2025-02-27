package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationFieldProjectPromotionMiddleRepository extends
    JpaRepository<RelationFieldProjectPromotionPostMiddle, Long> {

    Optional<RelationFieldProjectPromotionPostMiddle> findByProjectPromotionAndRelationFieldCategory(ProjectPromotion promotion, RelationFieldCategory relationFieldCategory);
}