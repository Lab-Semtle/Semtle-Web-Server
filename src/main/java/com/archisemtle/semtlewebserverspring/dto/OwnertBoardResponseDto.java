package com.archisemtle.semtlewebserverspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OwnertBoardResponseDto {
    @JsonProperty("board_id")
    private Long boardId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("create_date")
    private String createDate;
}
