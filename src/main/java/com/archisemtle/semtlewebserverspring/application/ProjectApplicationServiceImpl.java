package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Applicant;
import com.archisemtle.semtlewebserverspring.domain.Application;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicantsRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicationRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

    private final ApplicantsRepository applicantsRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public ProjectApplicantsResponseDto getApplicants(Long postId, int page, int limit) {
        if(postId == null || page == 0 || limit == 0) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Applicant> applicantsPage = applicantsRepository.findAllWithApplication(
            postId,
            pageable
        );

        if (applicantsPage == null || applicantsPage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_APPLICANTS);
        }

        ProjectApplicantsResponseDto projectApplicantsResponseDto = ProjectApplicantsResponseDto.entityToDto(
            applicantsPage,
            page
        );

        return projectApplicantsResponseDto;
    }


    @Override
    public ShowProjectApplicantInfoResponseDto getApplicantInfo(Long postId, Long applicantId) {
        if(postId == null || applicantId == 0) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }
        Applicant applicant = applicantsRepository.findByPostIdAndApplicantId(postId, applicantId).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_APPLICANT_FOUND));
        ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto = ShowProjectApplicantInfoResponseDto.entityToDto(
            applicant);
        return showProjectApplicantInfoResponseDto;
    }

    @Override
    @Transactional
    public ChangeApplyStatusResponseDto changeApplyStatus(Long postId, Long applicantId, String status) {

        if(postId == null || applicantId == null || status == null) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }

        Applicant applicant = applicantsRepository.findByPostIdAndApplicantId(postId, applicantId).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_APPLICANT_FOUND));

        // 이미 변경된(동일한) 상태
        if(applicant.getStatus().equals(status)) {
            throw new BaseException(BaseResponseStatus.PROCESSED_APPLICATION);
        }

        Applicant updatedApplicant = Applicant.builder()
            .applicantId(applicant.getApplicantId())
            .name(applicant.getName())
            .applyDate(applicant.getApplyDate())
            .status(status)
            .email(applicant.getEmail())
            .phone(applicant.getPhone())
            .updatedAt(LocalDateTime.now())
            .postId(applicant.getPostId())
            .build();

        applicantsRepository.save(updatedApplicant);


        Application application = applicationRepository.findByApplicantId(applicant.getApplicantId()).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_APPLICATIONS));

        Application updatedApplication = Application.builder()
            .applicationId(application.getApplicationId())
            .applicantId(application.getApplicantId())
            .memberId(application.getMemberId())
            .projectTitle(application.getProjectTitle())
            .postId(application.getPostId())
            .applyDate(application.getApplyDate())
            .status(status)
            .projectType(application.getProjectType())
            .relateField(application.getRelateField())
            .build();

        applicationRepository.save(updatedApplication);

        ChangeApplyStatusResponseDto changeApplyStatusResponseDto = ChangeApplyStatusResponseDto.entityToDto(
            updatedApplicant);
        return changeApplyStatusResponseDto;
    }

    @Override
    public ShowApplyingProjectInfoResponseDto getApplyingProjectInfo(int memberId, int page, int limit) {

        if(memberId == 0 || page == 0 || limit == 0) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Application> applicationsPage = applicationRepository.findByMemberId(memberId, pageable);

        if (applicationsPage == null || applicationsPage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_APPLICATIONS);
        }

        ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto = ShowApplyingProjectInfoResponseDto.entityToDto(
            applicationsPage,
            page
        );

        return showApplyingProjectInfoResponseDto;
    }
}
