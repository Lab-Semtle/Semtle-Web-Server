package com.archisemtle.semtlewebserverspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectBoardCUDResponseDto {
    private Long boardId;
    private String createDt;
    private String updateDt;
}
