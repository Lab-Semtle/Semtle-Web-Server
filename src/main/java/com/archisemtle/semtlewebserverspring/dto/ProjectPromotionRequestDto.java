package com.archisemtle.semtlewebserverspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectPromotionRequestDto {
    @JsonProperty("board_id")
    private Long boardId;

    @NotEmpty(message = "제목은 필수 입력값입니다.")
    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @NotEmpty(message = "작성자는 필수 입력값입니다.")
    @JsonProperty("writer")
    private String writer;

    @NotEmpty(message = "결과 링크는 필수 입력값입니다.")
    @JsonProperty("result_link")
    private String resultLink;

    @JsonProperty("image_url")
    private List<String> imageUrl;

    @NotEmpty(message = "프로젝트 게시물 작성일자는 필수 입력값입니다.")
    @JsonProperty("create_date")
    private String createDate;

    @NotEmpty(message = "프로젝트 마감일자는 필수 입력값입니다.")
    @JsonProperty("due_date")
    private String dueDate;

    @NotEmpty(message = "프로젝트 모집 종료일은 필수 입력값입니다.")
    @JsonProperty("recruiting_end_time")
    private String recruitingEndTime;

    @NotEmpty(message = "프로젝트 유형은 필수 입력값입니다.")
    @JsonProperty("project_type")
    private String projectType;

    @NotEmpty(message = "관련 분야는 필수 입력값입니다.")
    @JsonProperty("relate_field")
    private List<String> relateField;

    @JsonProperty("member")
    private String member;

    @NotEmpty(message = "내용은 유형은 필수 입력값입니다.")
    @JsonProperty("contents")
    private String contents;
}
