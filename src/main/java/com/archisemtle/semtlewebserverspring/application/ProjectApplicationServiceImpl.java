package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Apply;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.dto.ChangeApplyStatusResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ShowProjectApplicantInfoResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplyRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldProjectPostMiddleRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final ProjectBoardRepository projectBoardRepository;
    private final RelationFieldProjectPostMiddleRepository relationFieldProjectPostMiddleRepository;

    @Override
    public ProjectApplicantsResponseDto getApplicants(Long postId, int page, int limit) {
        if(postId == null || page == 0 || limit == 0) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }

        ProjectBoard projectBoard = projectBoardRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_BOARD_FOUND));

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Apply> applyPage = applyRepository.findByProjectBoard(
            projectBoard,
            pageable
        );

        if (applyPage == null) {
            throw new BaseException(BaseResponseStatus.NO_APPLICANTS);
        }

        ProjectApplicantsResponseDto projectApplicantsResponseDto = ProjectApplicantsResponseDto.entityToDto(
            applyPage,
            page
        );

        return projectApplicantsResponseDto;
    }


    @Override
    public ShowProjectApplicantInfoResponseDto getApplicantInfo(Long postId, UUID uuid) {
        if(postId == null || uuid == null) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }
        Member member = memberRepository.findByUuid(uuid)
            .orElseThrow(() -> new BaseException(
                BaseResponseStatus.NO_EXIST_MEMBERS));

        Apply apply = applyRepository.findByMemberAndProjectBoardId(member, postId);
        if(apply == null) {
            throw new BaseException(BaseResponseStatus.NO_APPLICANTS);
        }

        ShowProjectApplicantInfoResponseDto showProjectApplicantInfoResponseDto = ShowProjectApplicantInfoResponseDto.entityToDto(
            apply);
        return showProjectApplicantInfoResponseDto;
    }

    @Override
    @Transactional
    public ChangeApplyStatusResponseDto changeApplyStatus(Long postId, UUID uuid, String status) {

        if(postId == null || uuid == null || status == null) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }
        Member member = memberRepository.findByUuid(uuid)
            .orElseThrow(() -> new BaseException(
                BaseResponseStatus.NO_EXIST_MEMBERS));

        Apply apply = applyRepository.findByMemberAndProjectBoardId(member, postId);

        if (apply == null) {
            throw new BaseException(BaseResponseStatus.NO_APPLICATIONS);
        }

        if(!(status.equals("승인") || status.equals("대기") || status.equals("반려"))) {
            throw new BaseException(BaseResponseStatus.FALSE_STATUS);
        }

        if(apply.getStatus().equals(status)) {
            throw new BaseException(BaseResponseStatus.PROCESSED_APPLY);
        }

        Apply updatedApply = Apply.builder()
            .applyId(apply.getApplyId())
            .member(apply.getMember())
            .projectBoard(apply.getProjectBoard())
            .applyDate(apply.getApplyDate())
            .status(status)
            .updatedAt(LocalDateTime.now())
            .answer(apply.getAnswer())
            .build();

        applyRepository.save(updatedApply);

        ChangeApplyStatusResponseDto changeApplyStatusResponseDto = ChangeApplyStatusResponseDto.entityToDto(
            updatedApply);
        return changeApplyStatusResponseDto;
    }

    @Override
    public ShowApplyingProjectInfoResponseDto getApplyingProjectInfo(int page, int limit) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID uuid = UUID.fromString(authentication.getName());

        if(page == 0 || limit == 0) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }

        Member member = memberRepository.findByUuid(uuid)
            .orElseThrow(() -> new BaseException(
                BaseResponseStatus.NO_EXIST_MEMBERS));

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Apply> applyPage = applyRepository.findByMember(member, pageable);

        if (applyPage == null || applyPage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_APPLICATIONS);
        }

        ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto = ShowApplyingProjectInfoResponseDto.entityToDto(
            applyPage,
            page,
            relationFieldProjectPostMiddleRepository
        );

        return showApplyingProjectInfoResponseDto;
    }
}
