package com.archisemtle.semtlewebserverspring.vo.archive;

import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArchiveRequestVo {

    private String writer;
    private String content;
    private String title;
    private Date createdAt;

    public static ArchiveRequestDto voToDto(ArchiveRequestVo requestVo) {
        return ArchiveRequestDto.builder()
            .content(requestVo.getContent())
            .writer(requestVo.getWriter())
            .title(requestVo.getTitle())
            .createdAt(requestVo.getCreatedAt())
            .build();
    }

}
