package com.archisemtle.semtlewebserverspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectBoardResponseDto2 {
    @JsonProperty("board_id")
    private Long boardId;

    @JsonProperty("title")
    private String title;

//    @JsonProperty("subtitle")
//    private String subtitle;

    @JsonProperty("writer")
    private String writer;

    @JsonProperty("result_link")
    private String resultLink;

    @JsonProperty("image_url")
    private List<String> imageUrl;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("due_date")
    private String dueDate;

    @JsonProperty("project_type")
    private String projectType;

    @JsonProperty("relate_field")
    private List<String> relateField;

    @JsonProperty("member")
    private String member;

    @JsonProperty("contents")
    private String contents;
}
