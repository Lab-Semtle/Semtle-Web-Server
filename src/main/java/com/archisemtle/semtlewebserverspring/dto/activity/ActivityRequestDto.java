package com.archisemtle.semtlewebserverspring.dto.activity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class ActivityRequestDto {
    private String title;
    private String content;
    private String writer;
    private UUID uuid;
    private Date createdAt;
    private String type;
    private List<String> images;

}
