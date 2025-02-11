package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowProjectApplicantInfoResponseVo {
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

    @Builder
    public ShowProjectApplicantInfoResponseVo(
        Long applicantId,
        String name,
        Date applyDate,
        String status,
        String email,
        String phone,
        String resumeUrl,
        String portfolioUrl,
        String customAnswer,
        String additionalFile){
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
    }

    public static ShowProjectApplicantInfoResponseVo dtoToVo(
        ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto) {
        return ShowProjectApplicantInfoResponseVo.builder()
            .applicantId(showProjectApplicantInfoResponseDto.getApplicantId())
            .name(showProjectApplicantInfoResponseDto.getName())
            .applyDate(showProjectApplicantInfoResponseDto.getApplyDate())
            .status(showProjectApplicantInfoResponseDto.getStatus())
            .email(showProjectApplicantInfoResponseDto.getEmail())
            .phone(showProjectApplicantInfoResponseDto.getPhone())
            .resumeUrl(showProjectApplicantInfoResponseDto.getResumeUrl())
            .portfolioUrl(showProjectApplicantInfoResponseDto.getPortfolioUrl())
            .customAnswer(showProjectApplicantInfoResponseDto.getCustomAnswer())
            .additionalFile(showProjectApplicantInfoResponseDto.getAdditionalFile())
            .build();
    }
}
