package com.archisemtle.semtlewebserverspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectJoinAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectJoinAnswerId;

    @Column(nullable = false)
    private String answerText;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Long applicationId;

    @Builder
    public ProjectJoinAnswer(String answerText, Long questionId, Long applicationId) {
        this.answerText = answerText;
        this.questionId = questionId;
        this.applicationId = applicationId;
    }
}
