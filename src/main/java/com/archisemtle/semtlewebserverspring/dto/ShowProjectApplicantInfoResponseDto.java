package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Apply;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShowProjectApplicantInfoResponseDto {
    private Long applyId;
    private String username;
    private LocalDateTime applyDate;
    private String status;
    private String email;
    private String phone;

    public static ShowProjectApplicantInfoResponseDto entityToDto(Apply apply) {
        return ShowProjectApplicantInfoResponseDto.builder()
            .applyId(apply.getApplyId())
            .username(apply.getMember().getUsername())
            .applyDate(apply.getApplyDate())
            .status(apply.getStatus())
            .email(apply.getMember().getEmail())
            .phone(apply.getMember().getPhone())
            .build();
    }
}
