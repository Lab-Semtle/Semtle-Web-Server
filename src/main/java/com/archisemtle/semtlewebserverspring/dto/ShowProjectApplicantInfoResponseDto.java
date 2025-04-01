package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicant;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShowProjectApplicantInfoResponseDto {
    private Long applicantId;
    private String name;
    private LocalDate applyDate;
    private String status;
    private String email;
    private String phone;

    public static ShowProjectApplicantInfoResponseDto entityToDto(Applicant applicant) {
        return ShowProjectApplicantInfoResponseDto.builder()
            .applicantId(applicant.getApplicantId())
            .name(applicant.getName())
            .applyDate(applicant.getApplyDate())
            .status(applicant.getStatus())
            .email(applicant.getEmail())
            .phone(applicant.getPhone())
            .build();
    }
}
