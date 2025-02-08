package com.archisemtle.semtlewebserverspring.dto.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveResponseDto {

    private Long id;
    private String writer;
    private String content;
    private String title;
    private Date date;

    @Builder
    public ArchiveResponseDto(Archive archive) {
        id = archive.getId();
        writer = archive.getWriter();
        content = archive.getContent();
        title = archive.getTitle();
        date = archive.getDate();

    }



}
