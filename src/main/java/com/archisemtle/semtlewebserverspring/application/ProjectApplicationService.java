package com.archisemtle.semtlewebserverspring.application;


import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;

public interface ProjectApplicationService {
    ProjectApplicantsResponseDto getApplicants(Long boardId, int page, int limit) throws Exception;
    ShowProjectApplicantInfoResponseDto getApplicantInfo(Long boardId,Long applicantId) throws Exception;
    ChangeApplyStatusResponseDto changeApplyStatus(Long boardId,Long applicantId, String status) throws Exception;
    ShowApplyingProjectInfoResponseDto getApplyingProjectInfo(int memberId,int page, int limit) throws Exception;
}
