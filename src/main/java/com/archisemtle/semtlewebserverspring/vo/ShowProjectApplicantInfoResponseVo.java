package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowProjectApplicantInfoResponseVo {
    private Long applyId;
    private String username;
    private LocalDateTime applyDate;
    private String status;
    private String email;
    private String phone;

    @Builder
    public ShowProjectApplicantInfoResponseVo(
        Long applyId,
        String username,
        LocalDateTime applyDate,
        String status,
        String email,
        String phone){
        this.applyId = applyId;
        this.username = username;
        this.applyDate = applyDate;
        this.status = status;
        this.email = email;
        this.phone = phone;
    }

    public static ShowProjectApplicantInfoResponseVo dtoToVo(
        ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto) {
        return ShowProjectApplicantInfoResponseVo.builder()
            .applyId(showProjectApplicantInfoResponseDto.getApplyId())
            .username(showProjectApplicantInfoResponseDto.getUsername())
            .applyDate(showProjectApplicantInfoResponseDto.getApplyDate())
            .status(showProjectApplicantInfoResponseDto.getStatus())
            .email(showProjectApplicantInfoResponseDto.getEmail())
            .phone(showProjectApplicantInfoResponseDto.getPhone())
            .build();
    }
}
