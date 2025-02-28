package com.archisemtle.semtlewebserverspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OwnerResponseDto {
    @JsonProperty("total_items")
    private long  totalItems;
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("total_page")
    private int totalPage;
    @JsonProperty("projects")
    private List<OwnertBoardResponseDto> projects;
}
