package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.BannerService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.BannerListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.BannerRequestDto;
import com.archisemtle.semtlewebserverspring.dto.BannerResponseDto;
import com.archisemtle.semtlewebserverspring.vo.BannerListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.BannerResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/index")
public class BannerController {

    private final BannerService bannerService;

    // 배너 조회
    @GetMapping("/banners")
    public CommonResponse<BannerListResponseVo> getBanner() throws Exception{
            BannerListResponseDto bannerListResponseDto = bannerService.getBannerList();
            BannerListResponseVo bannerListResponseVo = BannerListResponseVo.dtoToVo(bannerListResponseDto.getBanners());
            return CommonResponse.success("배너 조회 성공", bannerListResponseVo);
    }

    // 배너 추가
    @PostMapping("/banners")
    public CommonResponse<BannerResponseVo> addBanner(@RequestBody BannerRequestDto bannerRequestDto)
        throws Exception {
        BannerResponseDto bannerResponseDto = bannerService.addBanner(bannerRequestDto);
        BannerResponseVo bannerResponseVo = BannerResponseVo.dtoToVo(bannerResponseDto);
        return CommonResponse.success("배너 추가 성공", bannerResponseVo);
    }

    // 배너 삭제
    @DeleteMapping("/banners/{banner_id}")
    public CommonResponse<Void> deleteBanner(@PathVariable("banner_id") Integer bannerId) throws Exception {
        bannerService.deleteBanner(bannerId);
        return CommonResponse.success("배너 삭제 성공");
    }
}
