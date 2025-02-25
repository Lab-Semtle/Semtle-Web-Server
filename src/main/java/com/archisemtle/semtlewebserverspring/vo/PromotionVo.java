package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectPromotionResponseDto2;
import com.archisemtle.semtlewebserverspring.dto.PromotionResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PromotionVo {
    @JsonProperty("total_items")
    private long  totalItems;
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("total_page")
    private int totalPage;
    @JsonProperty("projects")
    private List<ProjectPromotionResponseDto2> projects;

    public static PromotionVo dtoToVo(
            PromotionResponseDto dto)
    {
        return PromotionVo.builder()
                .totalItems(dto.getTotalItems())
                .currentPage(dto.getCurrentPage())
                .totalPage(dto.getTotalPage())
                .projects(dto.getProjects())
                .build();
    }
}
