package com.archisemtle.semtlewebserverspring.vo.archive;

import java.util.Date;
import java.util.List;
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
    private List<String> imageUrl;
    private List<String> fileUrl;

}
