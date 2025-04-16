package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyProjectResponseVo {
    private Long applyId;
    private LocalDateTime appliedAt;

    @Builder
    public ApplyProjectResponseVo(Long applyId, LocalDateTime appliedAt) {
        this.applyId = applyId;
        this.appliedAt = appliedAt;
    }

    public static ApplyProjectResponseVo dtoToVo(
        ApplyProjectResponseDto applyProjectResponseDto) {
        return ApplyProjectResponseVo.builder()
            .applyId(applyProjectResponseDto.getApplyId())
            .appliedAt(applyProjectResponseDto.getAppliedAt())
            .build();
    }
}
