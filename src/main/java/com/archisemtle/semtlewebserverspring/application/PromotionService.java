package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.*;

public interface PromotionService {
    PromotionResponseDto getPromotions(String keyword, int page, int size);
    ProjectPromotionResponseDto2 getPromotionsById(Long id);
    Boolean checkPermission(String uuid, Long id);
    ProjectPromotionCUDResponseDto mergePromotion (ProjectPromotionRequestDto reqDto);
    ProjectPromotionCUDResponseDto deletePromotion (Long id);
}
