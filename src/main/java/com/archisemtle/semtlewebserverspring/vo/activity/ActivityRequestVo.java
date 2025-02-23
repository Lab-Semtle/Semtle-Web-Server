package com.archisemtle.semtlewebserverspring.vo.activity;

import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityRequestVo {

    private String title;
    private String content;
    private String writer;
    private Date createdAt;
    private UUID uuid;
    private List<String> images;
    private String type;

    public static ActivityRequestDto voToDto(ActivityRequestVo requestVo){
        return ActivityRequestDto.builder()
            .title(requestVo.getTitle())
            .content(requestVo.getContent())
            .writer(requestVo.getWriter())
            .images(requestVo.getImages())
            .createdAt(requestVo.getCreatedAt())
            .uuid(requestVo.getUuid())
            .type(requestVo.getType())
            .build();
    }
}
