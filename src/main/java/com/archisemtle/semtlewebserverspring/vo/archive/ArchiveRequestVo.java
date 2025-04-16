package com.archisemtle.semtlewebserverspring.vo.archive;

import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArchiveRequestVo {

    private String writer;
    private String content;
    private String title;
    private UUID uuid;
    private List<String> imageUrl;
    private List<String> fileUrl;

    public static ArchiveRequestDto voToDto(ArchiveRequestVo requestVo) {
        return ArchiveRequestDto.builder()
            .content(requestVo.getContent())
            .writer(requestVo.getWriter())
            .title(requestVo.getTitle())
            .createdAt(LocalDateTime.now())
            .uuid(requestVo.getUuid())
            .imageUrl(requestVo.getImageUrl())
            .fileUrl(requestVo.getFileUrl())
            .build();
    }

}
