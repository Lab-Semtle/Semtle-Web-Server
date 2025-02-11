package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowMemberResponseDto;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeApplyStatusResponseVo {
    String message;
    String updatedStatus;
    String updatedAt;

    @Builder
    public ChangeApplyStatusResponseVo(String message, String updatedStatus, String updatedAt) {
        this.message = message;
        this.updatedStatus = updatedStatus;
        this.updatedAt = updatedAt;
    }

    public static ChangeApplyStatusResponseVo dtoToVo(
        ChangeApplyStatusResponseDto changeApplyStatusResponseDto) {
        return ChangeApplyStatusResponseVo.builder()
            .message(changeApplyStatusResponseDto.getMessage())
            .updatedStatus(changeApplyStatusResponseDto.getUpdatedStatus())
            .updatedAt(changeApplyStatusResponseDto.getUpdatedAt())
            .build();
    }
}
