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
            .appliedId(application.getApplicationId())
            .appliedAt(String.valueOf(application.getApplyDate()))
            .build();
    }
}
