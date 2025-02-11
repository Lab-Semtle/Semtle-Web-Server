package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Getter
@Entity(name = "applicants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicantId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date applyDate;

    @Column(nullable = false)
    private String status;

    @Column(unique = true)
    private String email;

    private String phone;

    private String resumeUrl;

    private String portfolioUrl;

    private String customAnswer;

    private String additionalFile;

    private String message;

    private String updatedStatus;

    private Date updatedAt;

    @Column(nullable = false)
    private int boardId; // 게시판 ID

    @Builder
    public Applicants(Integer applicantId,String name, Date applyDate, String status, String email, String phone,
        String resumeUrl, String portfolioUrl, String customAnswer,
        String additionalFile, String message, String updatedStatus, Date updatedAt, int boardId) {
        this.applicantId = applicantId;
        this.name = name;
        this.applyDate = applyDate;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.resumeUrl = resumeUrl;
        this.portfolioUrl = portfolioUrl;
        this.customAnswer = customAnswer;
        this.additionalFile = additionalFile;
        this.message = message;
        this.updatedStatus = updatedStatus;
        this.updatedAt = updatedAt;
        this.boardId = boardId;
    }
}
