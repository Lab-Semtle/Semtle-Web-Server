package com.archisemtle.semtlewebserverspring.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyProjectRequestDto {
    private List<AnswerDto> answers;
    private List<String> urls;
    private List<FileDto> files;

    @Getter
    @Setter
    @Builder
    public static class AnswerDto {
        private Integer questionId;
        private String answer;
    }

    @Getter
    @Setter
    @Builder
    public static class FileDto {
        private String fileName;
        private String fileUrl;
    }
}
