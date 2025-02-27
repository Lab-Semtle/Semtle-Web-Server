package com.archisemtle.semtlewebserverspring.dto.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import com.archisemtle.semtlewebserverspring.vo.activity.ActivityListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.activity.ActivityResponseVo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityListResponseDto {

    private int total_post;
    private int current_page;
    private int total_pages;
    private List<Activity> posts;

    public static ActivityListResponseVo dtoToVo(ActivityListResponseDto responseDto){
        return ActivityListResponseVo.builder()
            .total_post(responseDto.getTotal_post())
            .current_page(responseDto.getCurrent_page())
            .total_pages(responseDto.getTotal_pages())
            .posts(responseDto.getPosts().stream().map(activity -> Activity.entityToVo(activity)).collect(Collectors.toList()))
            .build();
    }

}
