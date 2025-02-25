package com.archisemtle.semtlewebserverspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectPromotionCUDResponseDto {
    private Long boardId;
    private String createDt;
    private String updateDt;
}
