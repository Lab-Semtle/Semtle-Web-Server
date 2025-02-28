package com.archisemtle.semtlewebserverspring.application;


import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;

public interface ProjectApplicationService {
    ProjectApplicantsResponseDto getApplicants(Integer boardId, int page, int limit) throws Exception;
    ShowProjectApplicantInfoResponseDto getApplicantInfo(Integer boardId,Integer applicantId) throws Exception;
    ChangeApplyStatusResponseDto changeApplyStatus(Integer boardId,Integer applicantId, String status) throws Exception;
    ShowApplyingProjectInfoResponseDto getApplyingProjectInfo(Integer applicantId,int page, int limit) throws Exception;
}
