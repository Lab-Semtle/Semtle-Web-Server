package com.archisemtle.semtlewebserverspring.dto.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveResponseVo;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveResponseDto {

    private String writer;
    private String content;
    private String title;
    private Date createdAt;

    @Builder
    public ArchiveResponseDto(Archive archive) {
        writer = archive.getWriter();
        content = archive.getContent();
        title = archive.getTitle();
        createdAt = archive.getCreatedAt();
    }

    public static ArchiveResponseVo dtoToVo(ArchiveResponseDto responseDto){
        return ArchiveResponseVo.builder()
            .writer(responseDto.getWriter())
            .content(responseDto.getContent())
            .title(responseDto.getTitle())
            .createdAt(responseDto.getCreatedAt())
            .build();
    }



}
