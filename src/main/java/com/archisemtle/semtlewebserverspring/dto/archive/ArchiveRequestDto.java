package com.archisemtle.semtlewebserverspring.dto.archive;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveRequestDto {

    private Long id;
    private String writer;
    private String content;
    private String title;
    private Date date;
    

}
