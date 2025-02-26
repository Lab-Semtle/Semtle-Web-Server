package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Banner;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BannerListResponseDto {

    private List<Banner> banners;

    public static BannerListResponseDto entityToDto(List<Banner> banners) {
        return BannerListResponseDto.builder().banners(banners).build();
    }
}
