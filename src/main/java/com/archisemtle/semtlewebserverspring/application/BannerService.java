package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.BannerListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.BannerRequestDto;
import com.archisemtle.semtlewebserverspring.dto.BannerResponseDto;
import java.util.List;

public interface BannerService {

    BannerListResponseDto getBannerList() throws Exception;
    BannerResponseDto addBanner(BannerRequestDto bannerRequestDto) throws Exception;
    void deleteBanner(Integer bannerId) throws Exception;
}
