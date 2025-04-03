package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    @Column(name = "member_id", nullable = false)
    private int memberId;

    @Column(nullable = false)
    private String projectTitle;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private LocalDate applyDate;

    @Column(nullable = false)
    private String status;

    private String projectType;

    private String relateField;


    @Builder
    public Application(Long applicationId, Long applicantId, int memberId ,String projectTitle, Long postId, LocalDate applyDate, String status,
        String projectType, String relateField) {
        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.memberId = memberId;
        this.projectTitle = projectTitle;
        this.postId = postId;
        this.applyDate = applyDate;
        this.status = status;
        this.projectType = projectType;
        this.relateField = relateField;
    }
}
