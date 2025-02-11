package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;

public interface ApplyProjectService {
    ApplyProjectResponseDto applyProject(int boardId, int applicantId, ApplyProjectRequestDto applyProjectRequestDto) throws Exception;
}
