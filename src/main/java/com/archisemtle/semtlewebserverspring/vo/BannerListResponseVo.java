package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.domain.Banner;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BannerListResponseVo {
    private List<Banner> banners;

    @Builder
    public BannerListResponseVo(List<Banner> banners) {
        this.banners = banners;
    }

    public static BannerListResponseVo dtoToVo(List<Banner> banners) {
        return BannerListResponseVo.builder().banners(banners).build();
    }
}
