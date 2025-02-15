package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.dto.*;

import java.util.ArrayList;

public interface PromotionService {
    PromotionResponseDto getPromotions(String keyword, int page, int size);
    ProjectBoardResponseDto2 getPromotionsById(Long id);
    ProjectBoardCUDResponseDto mergePromotion (ProjectBoardRequestDto reqDto);
    ProjectBoardCUDResponseDto deletePromotion (Long id);
}
