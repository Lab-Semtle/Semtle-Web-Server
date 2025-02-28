package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.Banner;
import com.archisemtle.semtlewebserverspring.dto.BannerListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.BannerRequestDto;
import com.archisemtle.semtlewebserverspring.dto.BannerResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.BannerRepository;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    public BannerListResponseDto getBannerList() throws Exception {
        List<Banner> banners = bannerRepository.findAll(); // 모든 배너 가져오기
        BannerListResponseDto bannerListResponseDto = BannerListResponseDto.entityToDto(banners);
        return bannerListResponseDto;
    }

    @Override
    @Transactional
    public BannerResponseDto addBanner(BannerRequestDto bannerRequestDto) throws Exception {

        String createdAt = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);

        Banner banner = Banner.builder()
            .imagePath(bannerRequestDto.getImagePath())
            .targetPath(bannerRequestDto.getTargetPath())
            .altText(bannerRequestDto.getAltText())
            .postTitle(bannerRequestDto.getPostTitle())
            .createdAt(createdAt)
            .build();

        bannerRepository.save(banner);
        BannerResponseDto bannerResponseDto = BannerResponseDto.entityToDto(banner);
        return bannerResponseDto;
    }

    @Override
    @Transactional
    public void deleteBanner(Integer bannerId) throws Exception {
        bannerRepository.deleteById(bannerId);
    }
}
