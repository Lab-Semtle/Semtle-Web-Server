package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import java.util.UUID;

public interface ProjectApplicationService {
    ProjectApplicantsResponseDto getApplicants(Long boardId, int page, int limit) throws Exception;
    ShowProjectApplicantInfoResponseDto getApplicantInfo(Long boardId,UUID uuid) throws Exception;
    ChangeApplyStatusResponseDto changeApplyStatus(Long boardId,UUID uuid, String status) throws Exception;
    ShowApplyingProjectInfoResponseDto getApplyingProjectInfo(int page, int limit) throws Exception;
}
