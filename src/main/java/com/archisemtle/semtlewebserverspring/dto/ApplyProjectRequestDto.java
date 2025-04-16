package com.archisemtle.semtlewebserverspring.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyProjectRequestDto {
    private Long postId;
    private String answer;
    private List<String> urls;
    private List<String> fileUrls;
}
