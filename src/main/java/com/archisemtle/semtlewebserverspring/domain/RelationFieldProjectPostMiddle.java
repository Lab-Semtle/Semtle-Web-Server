package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelationFieldProjectPostMiddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_field_project_post_middle_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_field_category_id")
    private RelationFieldCategory relationFieldCategory;

    public String getName(){
        return this.relationFieldCategory.getName();
    }

    @Builder
    public RelationFieldProjectPostMiddle(Long id, ProjectBoard projectBoard,
        RelationFieldCategory relationFieldCategory) {
        this.id = id;
        this.projectBoard = projectBoard;
        this.relationFieldCategory = relationFieldCategory;
    }
}
