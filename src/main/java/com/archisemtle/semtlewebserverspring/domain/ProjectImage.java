package com.archisemtle.semtlewebserverspring.domain;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_image_board_id")
    private ProjectBoard projectBoard;

    @Column(name = "project_image_board_url", length = 50)
    private String url;

    @Column(name = "project_image_board_thumbnail", nullable = false)
    private boolean thumbnail;          //0210추가

    @Builder
    public ProjectImage(Long id, ProjectBoard projectBoard, String url) {
        this.id = id;
        this.projectBoard = projectBoard;
        this.url = url;
    }
}
