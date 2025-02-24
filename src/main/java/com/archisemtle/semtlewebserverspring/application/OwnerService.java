package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto2;
import com.archisemtle.semtlewebserverspring.dto.PromotionResponseDto;

public interface OwnerService {
    OwnerResponseDto getPromotionsByOwnerId(int page, int size);
}
