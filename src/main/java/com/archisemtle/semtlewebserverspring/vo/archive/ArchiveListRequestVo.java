package com.archisemtle.semtlewebserverspring.vo.archive;


import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArchiveListRequestVo {

    private int page;
    private int size;
    private String search_keyword;

    public static ArchiveListRequestDto voToDto(ArchiveListRequestVo requestVo){
        return ArchiveListRequestDto.builder()
            .page(requestVo.getPage())
            .size(requestVo.getSize())
            .search_keyword(requestVo.getSearch_keyword())
            .build();
    }

}
