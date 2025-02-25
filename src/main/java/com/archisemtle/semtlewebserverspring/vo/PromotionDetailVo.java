package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectBoardRequestDto2;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PromotionDetailVo {
    @JsonProperty("board_id")
    private Long boardId;

    @JsonProperty("title")
    private String title;

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

    @JsonProperty("end_date")
    private String recruitingEndTime;

    @JsonProperty("project_type")
    private String projectType;

    @JsonProperty("relate_field")
    private List<String> relateField;

    @JsonProperty("member")
    private String member;

    @JsonProperty("contents")
    private String contents;

    public static PromotionDetailVo dtoToVo(
            ProjectBoardRequestDto2 dto)
    {
        return PromotionDetailVo.builder()
                .boardId(dto.getBoardId())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .resultLink(dto.getResultLink())
                .imageUrl(dto.getImageUrl())
                .createDate(dto.getCreateDate())
                .dueDate(dto.getDueDate())
                .recruitingEndTime(dto.getRecruitingEndTime())
                .projectType(dto.getProjectType())
                .relateField(dto.getRelateField())
                .contents(dto.getContents())
                .build();
    }
}
