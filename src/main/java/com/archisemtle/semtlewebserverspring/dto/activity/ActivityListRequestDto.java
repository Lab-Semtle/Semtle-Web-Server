package com.archisemtle.semtlewebserverspring.dto.activity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityListRequestDto {

    private int page;
    private int size;
    private String type;

}
