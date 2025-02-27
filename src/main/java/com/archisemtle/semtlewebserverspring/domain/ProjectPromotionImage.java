package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectPromotionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_promotion_id")
    private ProjectPromotion projectPromotion;

    @Column(name = "project_image_board_url", length = 50)
    private String url;

    @Column(name = "project_image_board_thumbnail", nullable = false)
    private boolean thumbnail;          //0210추가

    @Builder
    public ProjectPromotionImage(Long id,  ProjectPromotion projectPromotion, String url) {
        this.id = id;
        this.projectPromotion = projectPromotion;
        this.url = url;
    }
}
