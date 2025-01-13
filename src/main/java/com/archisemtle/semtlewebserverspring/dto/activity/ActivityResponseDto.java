package com.archisemtle.semtlewebserverspring.dto.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActivityResponseDto {

    private String title;
    private String content;
    private String writer;
    private Date createDate;
    private List<ActivityImageResponseDto> images;

    public ActivityResponseDto(Activity activity){
        this.title = activity.getTitle();
        this.content = activity.getContent();
        this.writer = activity.getWriter();
        this.createDate = activity.getCreateDate();
        this.images = activity.getImages().stream()
            .map(ActivityImageResponseDto::new).collect(Collectors.toList());
    }
}
