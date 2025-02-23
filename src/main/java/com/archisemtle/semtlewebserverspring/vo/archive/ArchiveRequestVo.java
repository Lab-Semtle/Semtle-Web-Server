package com.archisemtle.semtlewebserverspring.vo.archive;

import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArchiveRequestVo {

    private String writer;
    private String content;
    private String title;
    private UUID uuid;
    private Date createdAt;

    public static ArchiveRequestDto voToDto(ArchiveRequestVo requestVo) {
        return ArchiveRequestDto.builder()
            .content(requestVo.getContent())
            .writer(requestVo.getWriter())
            .title(requestVo.getTitle())
            .createdAt(requestVo.getCreatedAt())
            .uuid(requestVo.getUuid())
            .build();
    }

}
