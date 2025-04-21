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
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectJoinFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectJoinFileId;

    @Column(nullable = false)
    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id", nullable = false)
    @Comment("신청 ID")
    private Apply apply;

    @Builder
    public ProjectJoinFile(String file, Apply apply) {
        this.file = file;
        this.apply = apply;
    }
}
