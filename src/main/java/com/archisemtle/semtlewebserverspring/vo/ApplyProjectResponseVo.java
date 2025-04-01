package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyProjectResponseVo {
    private String message;
    private Long applicationId;
    private LocalDateTime appliedAt;

    @Builder
    public ApplyProjectResponseVo(String message, Long applicationId, LocalDateTime appliedAt) {
        this.message = message;
        this.applicationId = applicationId;
        this.appliedAt = appliedAt;
    }

    public static ApplyProjectResponseVo dtoToVo(
        ApplyProjectResponseDto applyProjectResponseDto) {
        return ApplyProjectResponseVo.builder()
            .message(applyProjectResponseDto.getMessage())
            .applicationId(applyProjectResponseDto.getApplicationId())
            .appliedAt(applyProjectResponseDto.getAppliedAt())
            .build();
    }
}
