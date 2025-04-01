package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectJoinFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectJoinFileId;

    @Column(nullable = false)
    private String file;

    @Column(nullable = false)
    private Long applicationId;

    @Builder
    public ProjectJoinFile(String file, Long applicationId) {
        this.file = file;
        this.applicationId = applicationId;
    }
}
