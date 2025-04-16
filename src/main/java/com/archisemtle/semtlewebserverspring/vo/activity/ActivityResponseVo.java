package com.archisemtle.semtlewebserverspring.vo.activity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ActivityResponseVo {

    private Long board_id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private List<String> images;
    private String type;

}
