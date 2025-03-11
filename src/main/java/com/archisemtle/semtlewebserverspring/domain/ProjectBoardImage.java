package com.archisemtle.semtlewebserverspring.domain;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectBoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_board_image_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;
    @Column(name ="project_board_image_url")
    private String projectBoardImageUrl;

    @Builder
    public ProjectBoardImage(Long id, ProjectBoard projectBoard, String projectBoardImageUrl) {
        this.id = id;
        this.projectBoard = projectBoard;
        this.projectBoardImageUrl = projectBoardImageUrl;
    }
}
