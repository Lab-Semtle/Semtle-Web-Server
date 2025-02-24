package com.archisemtle.semtlewebserverspring.vo.archive;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchiveResponseVo {

    private Long board_id;
    private String writer;
    private String content;
    private String title;
    private Date createdAt;

}
