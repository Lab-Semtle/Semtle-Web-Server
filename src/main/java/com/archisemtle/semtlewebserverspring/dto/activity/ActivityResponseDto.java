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

    private String title;
    private String content;
    private String writer;
    private Date createdAt;
    private List<String> images;

    @Builder
    public ActivityResponseDto(Activity activity){
        this.title = activity.getTitle();
        this.content = activity.getContent();
        this.writer = activity.getWriter();
        this.createdAt = activity.getCreatedAt();
        this.images = activity.getImages();
    }

    public static ActivityResponseVo dtoToVo(ActivityResponseDto responseDto){
        return ActivityResponseVo.builder()
            .title(responseDto.getTitle())
            .content(responseDto.getContent())
            .writer(responseDto.getWriter())
            .createdAt(responseDto.getCreatedAt())
            .images(responseDto.getImages())
            .build();
    }
}
