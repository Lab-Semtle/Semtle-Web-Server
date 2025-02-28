package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyProjectResponseVo {
    private String message;
    private Integer appliedId;
    private String appliedAt;

    @Builder
    public ApplyProjectResponseVo(String message, Integer appliedId, String appliedAt) {
        this.message = message;
        this.appliedId = appliedId;
        this.appliedAt = appliedAt;
    }

    public static ApplyProjectResponseVo dtoToVo(
        ApplyProjectResponseDto applyProjectResponseDto) {
        return ApplyProjectResponseVo.builder()
            .message(applyProjectResponseDto.getMessage())
            .appliedId(applyProjectResponseDto.getAppliedId())
            .appliedAt(applyProjectResponseDto.getAppliedAt())
            .build();
    }
}
