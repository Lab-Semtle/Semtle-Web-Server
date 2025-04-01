package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Application;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyProjectResponseDto {
    private String message;
    private Long applicationId;
    private LocalDateTime appliedAt;

    public static ApplyProjectResponseDto entityToDto(Application application) {
        if(application == null) {
            return ApplyProjectResponseDto.builder()
                .message("모집기간이 마감되었습니다.")
                .applicationId(null)
                .appliedAt(null)
                .build();
        }else {
            return ApplyProjectResponseDto.builder()
                .message("프로젝트 신청이 성공적으로 접수되었습니다.")
                .applicationId(application.getApplicationId())
                .appliedAt(LocalDateTime.now())
                .build();
        }
    }
}
