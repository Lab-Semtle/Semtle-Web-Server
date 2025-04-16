package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "apply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id", nullable = false)
    private ProjectBoard projectBoard;

    @Column(nullable = false)
    private LocalDateTime applyDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Lob
    @Column(nullable = false)
    private String answer;

    @Builder
    public Apply(Long applyId, Member member, ProjectBoard projectBoard, LocalDateTime applyDate, String status, LocalDateTime updatedAt, String answer) {
        this.applyId = applyId;
        this.member = member;
        this.projectBoard = projectBoard;
        this.applyDate = applyDate;
        this.status = status;
        this.updatedAt = updatedAt;
        this.answer = answer;
    }
}
