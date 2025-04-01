package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import java.time.LocalDate;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowProjectApplicantInfoResponseVo {
    private Long applicantId;
    private String name;
    private LocalDate applyDate;
    private String status;
    private String email;
    private String phone;

    @Builder
    public ShowProjectApplicantInfoResponseVo(
        Long applicantId,
        String name,
        LocalDate applyDate,
        String status,
        String email,
        String phone){
        this.applicantId = applicantId;
        this.name = name;
        this.applyDate = applyDate;
        this.status = status;
        this.email = email;
        this.phone = phone;
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
            .build();
    }
}
