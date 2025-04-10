package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeApplyStatusResponseVo {
    String updatedStatus;
    LocalDateTime updatedAt;

    @Builder
    public ChangeApplyStatusResponseVo(String updatedStatus, LocalDateTime updatedAt) {
        this.updatedStatus = updatedStatus;
        this.updatedAt = updatedAt;
    }

    public static ChangeApplyStatusResponseVo dtoToVo(
        ChangeApplyStatusResponseDto changeApplyStatusResponseDto) {
        return ChangeApplyStatusResponseVo.builder()
            .updatedStatus(changeApplyStatusResponseDto.getUpdatedStatus())
            .updatedAt(changeApplyStatusResponseDto.getUpdatedAt())
            .build();
    }
}
