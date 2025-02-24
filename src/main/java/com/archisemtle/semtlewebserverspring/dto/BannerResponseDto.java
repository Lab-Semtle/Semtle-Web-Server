package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Banner;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BannerResponseDto {
    private String imagePath;
    private String targetPath;
    private String altText;
    private String postTitle;
    private String createdAt;

    public static BannerResponseDto entityToDto(Banner banner) {
        return BannerResponseDto.builder()
            .imagePath(banner.getImagePath())
            .targetPath(banner.getTargetPath())
            .altText(banner.getAltText())
            .postTitle(banner.getPostTitle())
            .createdAt(banner.getCreatedAt())
            .build();
    }
}
