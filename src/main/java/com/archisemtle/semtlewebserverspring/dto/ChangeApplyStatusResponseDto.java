package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicants;
import java.time.LocalDateTime;
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
    private String updatedAt;

    public static ChangeApplyStatusResponseDto entityToDto(Applicants applicants) {
        return ChangeApplyStatusResponseDto.builder()
            .message("신청자의 상태가 성공적으로 변경되었습니다.")
            .updatedStatus(applicants.getStatus())
            .updatedAt(String.valueOf(LocalDateTime.now()))
            .build();
    }
}
