package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicants;
import com.archisemtle.semtlewebserverspring.domain.Application;
import com.archisemtle.semtlewebserverspring.domain.ShowMember;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyProjectResponseDto {
    private String message;
    private Integer appliedId;
    private String appliedAt;

    public static ApplyProjectResponseDto entityToDto(Application application) {
        return ApplyProjectResponseDto.builder()
            .message("프로젝트 신청이 성공적으로 접수되었습니다.")
            .appliedId(application.getApplicationId())
            .appliedAt(LocalDateTime.now().toString())
            .build();
    }
}
