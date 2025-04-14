package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;

public interface ApplyProjectService {
    ApplyProjectResponseDto applyProject(ApplyProjectRequestDto applyProjectRequestDto) throws Exception;
}
