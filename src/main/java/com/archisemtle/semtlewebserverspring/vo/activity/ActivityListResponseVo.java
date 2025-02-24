package com.archisemtle.semtlewebserverspring.vo.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityListResponseVo {

    private int total_post;
    private int current_page;
    private int total_pages;
    private List<ActivityResponseVo> posts;

}
