package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicants;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeApplyStatusResponseDto {
    private String message;
    private String updatedStatus;
    private Date updatedAt;

    public static ChangeApplyStatusResponseDto entityToDto(Applicants applicants) {
        return ChangeApplyStatusResponseDto.builder()
            .updatedStatus(applicants.getUpdatedStatus())
            .updatedAt(applicants.getUpdatedAt())
            .build();
    }
}
