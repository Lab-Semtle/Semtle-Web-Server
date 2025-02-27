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
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bannerId;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String targetPath;

    private String altText;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String createdAt;

    @Builder
    public Banner(
        String imagePath,
        String targetPath,
        String altText,
        String postTitle,
        String createdAt
    ) {
        this.imagePath = imagePath;
        this.targetPath = targetPath;
        this.altText = altText;
        this.postTitle = postTitle;
        this.createdAt = createdAt;
    }
}
