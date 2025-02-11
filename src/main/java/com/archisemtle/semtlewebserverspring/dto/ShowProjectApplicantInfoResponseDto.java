package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicants;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShowProjectApplicantInfoResponseDto {
    private Long applicantId;
    private String name;
    private Date applyDate;
    private String status;
    private String email;
    private String phone;
    private String resumeUrl;
    private String portfolioUrl;
    private String customAnswer;
    private String additionalFile;

    public static ShowProjectApplicantInfoResponseDto entityToDto(Applicants applicants) {
        return ShowProjectApplicantInfoResponseDto.builder()
            .applicantId(applicants.getApplicantId())
            .name(applicants.getName())
            .applyDate(applicants.getApplyDate())
            .status(applicants.getStatus())
            .email(applicants.getEmail())
            .phone(applicants.getPhone())
            .resumeUrl(applicants.getResumeUrl())
            .portfolioUrl(applicants.getPortfolioUrl())
            .customAnswer(applicants.getCustomAnswer())
            .additionalFile(applicants.getAdditionalFile())
            .build();
    }
}
