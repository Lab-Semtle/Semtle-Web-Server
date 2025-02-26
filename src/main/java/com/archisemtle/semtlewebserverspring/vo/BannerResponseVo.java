package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.BannerResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BannerResponseVo {

    private String imagePath;
    private String targetPath;
    private String altText;
    private String postTitle;
    private String createdAt;

    @Builder
    public BannerResponseVo(
        String imagePath,
        String targetPath,
        String altText,
        String postTitle,
        String createdAt
    ) {
        this.imagePath = imagePath;
        this.targetPath = targetPath;
        this.altText = altText;
        this.postTitle = postTitle;
        this.createdAt = createdAt;
    }

    public static BannerResponseVo dtoToVo(
        BannerResponseDto bannerResponseDto) {
        return BannerResponseVo.builder()
            .imagePath(bannerResponseDto.getImagePath())
            .targetPath(bannerResponseDto.getTargetPath())
            .altText(bannerResponseDto.getAltText())
            .postTitle(bannerResponseDto.getPostTitle())
            .createdAt(bannerResponseDto.getCreatedAt())
            .build();
    }
}
