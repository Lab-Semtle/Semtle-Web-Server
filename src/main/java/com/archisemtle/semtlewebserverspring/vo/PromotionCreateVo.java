package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.PromotionCUDDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromotionCreateVo {
    @JsonProperty("message")
    private String message;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("created_at")
    private String createdAt;

    public static PromotionCreateVo dtoToVo(
            PromotionCUDDtos.Create dto)
    {
        return PromotionCreateVo.builder()
                .message(dto.getMessage())
                .projectId(dto.getProjectId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
