package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Apply;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyProjectResponseDto {
    private Long applyId;
    private LocalDateTime appliedAt;

    public static ApplyProjectResponseDto entityToDto(Apply apply) {
        return ApplyProjectResponseDto.builder()
            .applyId(apply.getApplyId())
            .appliedAt(LocalDateTime.now())
            .build();
    }
}
