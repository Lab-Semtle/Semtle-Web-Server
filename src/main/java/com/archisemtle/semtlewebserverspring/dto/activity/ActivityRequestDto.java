package com.archisemtle.semtlewebserverspring.dto.activity;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActivityRequestDto {
    private String title;
    private String content;
    private String writer;
    private Date createDate;

}
