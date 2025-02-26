package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.OwnertBoardResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OwnPromotionVo {
    @JsonProperty("total_items")
    private long  totalItems;
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("total_page")
    private int totalPage;
    @JsonProperty("projects")
    private List<OwnertBoardResponseDto> projects;

    public static OwnPromotionVo dtoToVo(
            OwnerResponseDto dto)
    {
        return OwnPromotionVo.builder()
                .totalItems(dto.getTotalItems())
                .currentPage(dto.getCurrentPage())
                .totalPage(dto.getTotalPage())
                .projects(dto.getProjects())
                .build();
    }
}
