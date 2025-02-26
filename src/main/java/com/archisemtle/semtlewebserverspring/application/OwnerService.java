package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;

public interface OwnerService {
    OwnerResponseDto getPromotionsByOwnerId(int page, int size, String uuid);
}
