package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.PromotionCUDDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromotionUpdateVo {
    @JsonProperty("message")
    private String message;
    @JsonProperty("updated_at")
    private String updatedAt;

    public static PromotionUpdateVo dtoToVo(
            PromotionCUDDtos.Update dto)
    {
        return PromotionUpdateVo.builder()
                .message(dto.getMessage())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
