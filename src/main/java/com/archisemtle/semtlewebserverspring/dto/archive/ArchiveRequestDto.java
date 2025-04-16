package com.archisemtle.semtlewebserverspring.dto.archive;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private UUID uuid;
    private LocalDateTime createdAt;
    private List<String> imageUrl;
    private List<String> fileUrl;
    

}
