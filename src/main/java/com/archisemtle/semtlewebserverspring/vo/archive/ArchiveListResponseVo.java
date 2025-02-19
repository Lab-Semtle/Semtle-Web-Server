package com.archisemtle.semtlewebserverspring.vo.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArchiveListResponseVo {

    private int total_post;
    private int current_page;
    private int total_pages;
    private List<Archive> posts;

}
