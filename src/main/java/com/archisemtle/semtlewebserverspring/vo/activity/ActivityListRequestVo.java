package com.archisemtle.semtlewebserverspring.vo.activity;

import com.archisemtle.semtlewebserverspring.dto.activity.ActivityListRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityListRequestVo {

    private int page;
    private int size;
    private String type;

    public static ActivityListRequestDto voToDto(ActivityListRequestVo requestVo){
        return ActivityListRequestDto.builder()
            .page(requestVo.getPage())
            .size(requestVo.getSize())
            .type(requestVo.getType())
            .build();
    }

}
