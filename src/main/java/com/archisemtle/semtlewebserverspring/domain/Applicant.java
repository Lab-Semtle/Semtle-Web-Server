package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "applicants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate applyDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String email;

    private String phone;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Long postId;

    @Builder
    public Applicant(Long applicantId, String name, LocalDate applyDate, String status, String email, String phone, LocalDateTime updatedAt , Long postId) {
        this.applicantId = applicantId;
        this.name = name;
        this.applyDate = applyDate;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.updatedAt = updatedAt;
        this.postId = postId;
    }

}

