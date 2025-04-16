package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Apply;
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

    public static ChangeApplyStatusResponseDto entityToDto(Apply apply) {
        return ChangeApplyStatusResponseDto.builder()
            .updatedStatus(apply.getStatus())
            .updatedAt(apply.getUpdatedAt())
            .build();
    }
}
