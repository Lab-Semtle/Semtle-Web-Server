package com.archisemtle.semtlewebserverspring.dto.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import com.archisemtle.semtlewebserverspring.vo.activity.ActivityResponseVo;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ActivityResponseDto {

    private Long board_id;
    private String title;
    private String content;
    private String writer;
    private Date createdAt;
    private List<String> images;
    private String type;

    @Builder
    public ActivityResponseDto(Activity activity){
        this.board_id = activity.getBoardId();
        this.title = activity.getTitle();
        this.content = activity.getContent();
        this.writer = activity.getWriter();
        this.createdAt = activity.getCreatedAt();
        this.images = activity.getImages();
        this.type = activity.getType();
    }

    public static ActivityResponseVo dtoToVo(ActivityResponseDto responseDto){
        return ActivityResponseVo.builder()
            .title(responseDto.getTitle())
            .content(responseDto.getContent())
            .writer(responseDto.getWriter())
            .createdAt(responseDto.getCreatedAt())
            .images(responseDto.getImages())
            .type(responseDto.getType())
            .board_id(responseDto.getBoard_id())
            .build();
    }
}
