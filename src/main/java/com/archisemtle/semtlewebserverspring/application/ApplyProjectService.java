package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;

public interface ApplyProjectService {
    ApplyProjectResponseDto applyProject(Integer boardId, Integer applicantId, ApplyProjectRequestDto applyProjectRequestDto) throws Exception;
}
