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
public class ProjectJoinUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectJoinUrlId;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long applicationId;

    @Builder
    public ProjectJoinUrl(String url, Long applicationId) {
        this.url = url;
        this.applicationId = applicationId;
    }
}
