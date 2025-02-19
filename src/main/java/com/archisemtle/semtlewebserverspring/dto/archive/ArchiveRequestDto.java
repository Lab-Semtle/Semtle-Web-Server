package com.archisemtle.semtlewebserverspring.dto.archive;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ArchiveRequestDto {

    private String writer;
    private String content;
    private String title;
    private Date createdAt;
    

}
