package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelationFieldProjectPromotionPostMiddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_field_project_post_middle_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_promotion_id")
    private ProjectPromotion projectPromotion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_field_category_id")
    private RelationFieldCategory relationFieldCategory;

    public String getName(){
        return this.relationFieldCategory.getName();
    }

    @Builder
    public RelationFieldProjectPromotionPostMiddle(Long id, ProjectPromotion projectPromotion,
                                                   RelationFieldCategory relationFieldCategory) {
        this.id = id;
        this.projectPromotion = projectPromotion;
        this.relationFieldCategory = relationFieldCategory;
    }
}
