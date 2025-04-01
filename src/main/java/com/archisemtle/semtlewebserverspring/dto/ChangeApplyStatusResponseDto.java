package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicant;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeApplyStatusResponseDto {
    private String updatedStatus;
    private LocalDateTime updatedAt;

    public static ChangeApplyStatusResponseDto entityToDto(Applicant applicant) {
        return ChangeApplyStatusResponseDto.builder()
            .updatedStatus(applicant.getStatus())
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
