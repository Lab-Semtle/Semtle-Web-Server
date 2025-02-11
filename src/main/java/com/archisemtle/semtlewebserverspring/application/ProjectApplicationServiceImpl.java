package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.Applicants;
import com.archisemtle.semtlewebserverspring.domain.Application;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicantsRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicationRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
    public ProjectApplicantsResponseDto getApplicants(Integer boardId, int page, int limit)
        throws Exception {

        // 게시글 ID로 지원자 목록 조회 (페이징 처리)
        Pageable pageable = PageRequest.of(page - 1, limit); // Pageable로 선언
        Page<Applicants> applicantsPage = applicantsRepository.findAllWithApplication(
            boardId,
            pageable
        );

        // 결과가 비어있거나 지원자 목록 조회에 실패한 경우 처리
        if (applicantsPage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.APPLICATION_NOT_FOUND);
        }

        // 지원자 정보를 DTO로 변환
        List<Applicants> applicants = applicantsPage.getContent();

        // DTO 생성
        ProjectApplicantsResponseDto projectApplicantsResponseDto = ProjectApplicantsResponseDto.entityToDto(
            applicants,
            page,
            (int) applicantsPage.getTotalElements()
        );

        return projectApplicantsResponseDto;
    }


    @Override
    public ShowProjectApplicantInfoResponseDto getApplicantInfo(Integer boardId, Integer applicantId) throws Exception {
        Applicants applicants = applicantsRepository.findByBoardIdAndApplicantId(boardId, applicantId).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_APPLICANT_FOUND));

        ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto = ShowProjectApplicantInfoResponseDto.entityToDto(
            applicants);
        return showProjectApplicantInfoResponseDto;
    }

    @Override
    @Transactional
    public ChangeApplyStatusResponseDto changeApplyStatus(Integer boardId, Integer applicantId,
        String status) throws Exception {
        // 신청자 정보 조회
        Applicants applicants = applicantsRepository.findByBoardIdAndApplicantId(boardId, applicantId).orElseThrow(() -> new BaseException(
            BaseResponseStatus.NO_APPLICANT_FOUND));

        Applicants updatedApplicants = Applicants.builder()
            .applicantId(applicants.getApplicantId())
            .name(applicants.getName())
            .applyDate(applicants.getApplyDate())
            .status(status)
            .email(applicants.getEmail())
            .phone(applicants.getPhone())
            .resumeUrl(applicants.getResumeUrl())
            .portfolioUrl(applicants.getPortfolioUrl())
            .customAnswer(applicants.getCustomAnswer())
            .additionalFile(applicants.getAdditionalFile())
            .updatedAt(String.valueOf(LocalDateTime.now()))
            .boardId(applicants.getBoardId())
            .build();

        applicantsRepository.save(updatedApplicants);

        ChangeApplyStatusResponseDto changeApplyStatusResponseDto = ChangeApplyStatusResponseDto.entityToDto(updatedApplicants);
        return changeApplyStatusResponseDto;
    }

    @Override
    public ShowApplyingProjectInfoResponseDto getApplyingProjectInfo(Integer applicantId, int page, int limit)
        throws Exception {

        // 게시글 ID로 지원자 목록 조회 (페이징 처리)
        Pageable pageable = PageRequest.of(page - 1, limit); // Pageable로 선언
        Page<Application> applicationsPage = applicationRepository.findByApplicantId(applicantId, pageable);

        // 결과가 비어있거나 신청서 조회에 실패한 경우 처리
        if (applicationsPage.isEmpty()) {
            System.out.println("신청한 공고가 없거나 조회에 실패했습니다.");
            return null;
        }

        // 지원자 정보를 DTO로 변환
        List<Application> applications = applicationsPage.getContent();

        // DTO 생성
        ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto = ShowApplyingProjectInfoResponseDto.entityToDto(
            applications,
            page,
            (int) applicationsPage.getTotalElements()
        );

        return showApplyingProjectInfoResponseDto;
    }
}
