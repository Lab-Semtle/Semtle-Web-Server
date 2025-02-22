package com.archisemtle.semtlewebserverspring.dto.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveResponseVo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchiveListResponseDto {

    private int total_post;
    private int current_page;
    private int total_pages;
    private List<Archive> posts;

    public static ArchiveListResponseVo dtoToVo(ArchiveListResponseDto responseDto){
        return ArchiveListResponseVo.builder()
            .total_post(responseDto.getTotal_post())
            .current_page(responseDto.getCurrent_page())
            .total_pages(responseDto.getTotal_pages())
            .posts(responseDto.getPosts().stream()
                .map(archive -> Archive.entityToVo(archive)).collect(Collectors.toList()))
            .build();
    }


}
