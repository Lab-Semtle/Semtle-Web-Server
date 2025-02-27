package com.archisemtle.semtlewebserverspring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BannerRequestDto {

    private String imagePath;
    private String targetPath;
    private String altText;
    private String postTitle;

}
