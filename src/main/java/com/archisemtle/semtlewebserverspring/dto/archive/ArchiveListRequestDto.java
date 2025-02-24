package com.archisemtle.semtlewebserverspring.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArchiveListRequestDto {

    private int page;
    private int size;
    private String search_keyword;


}
