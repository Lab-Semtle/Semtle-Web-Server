package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Applicant;
import com.archisemtle.semtlewebserverspring.domain.Application;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.ProjectJoinAnswer;
import com.archisemtle.semtlewebserverspring.domain.ProjectJoinFile;
import com.archisemtle.semtlewebserverspring.domain.ProjectJoinUrl;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicantsRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicationRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectJoinAnswerRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectJoinFileRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectJoinUrlRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldProjectPostMiddleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyProjectServiceImpl implements ApplyProjectService {

    private final ApplicantsRepository applicantsRepository;
    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final ProjectBoardRepository projectBoardRepository;
    private final RelationFieldProjectPostMiddleRepository relationFieldProjectPostMiddleRepository;
    private final ProjectJoinAnswerRepository projectJoinAnswerRepository;
    private final ProjectJoinFileRepository projectJoinFileRepository;
    private final ProjectJoinUrlRepository projectJoinUrlRepository;

    @Override
    @Transactional
    public ApplyProjectResponseDto applyProject(Long postId, Long applicantId, ApplyProjectRequestDto applyProjectRequestDto) {

        if(postId == null || applicantId == null || applyProjectRequestDto.getAnswers() == null) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }

        ProjectBoard projectBoard = projectBoardRepository.findById(postId)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_BOARD_FOUND));

        if(LocalDate.now().isBefore(projectBoard.getProjectRecruitingEndTime().plusDays(1))) {

            Member member = memberRepository.findById(Math.toIntExact(applicantId))
                .orElseThrow(() -> new BaseException(
                    BaseResponseStatus.NO_EXIST_MEMBERS));

            List<Application> existingApplications = applicationRepository.findByMemberIdAndPostId(member.getMemberId(), postId);

            if (!existingApplications.isEmpty()) {
                throw new BaseException(BaseResponseStatus.DUPLICATE_APPLICATION);
            }

            Applicant applicant = Applicant.builder()
                .name(member.getUsername())
                .applyDate(LocalDate.now())
                .status("대기")
                .email(member.getEmail())
                .phone(member.getPhone())
                .updatedAt(LocalDateTime.now())
                .postId(postId)
                .build();

            applicantsRepository.save(applicant);

            ProjectTypeCategory projectTypeName = projectBoard.getProjectTypeCategory();
            List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddles = relationFieldProjectPostMiddleRepository.findAllByProjectBoardId(postId);

            List<String> relationFieldNames = relationFieldProjectPostMiddles.stream()
                .map(relationField -> relationField.getRelationFieldCategory().getName())
                .toList();

            Application application = Application.builder()
                .applicantId(applicant.getApplicantId())
                .memberId(member.getMemberId())
                .projectTitle(projectBoard.getTitle())
                .postId(postId)
                .applyDate(LocalDate.now())
                .status("대기")
                .projectType(projectTypeName.getName())
                .relateField(relationFieldNames.toString())
                .build();

            applicationRepository.save(application);


            List<ProjectJoinUrl> projectJoinUrls = applyProjectRequestDto.getUrls().stream()
                .map(url -> ProjectJoinUrl.builder()
                    .url(url)
                    .applicationId(application.getApplicationId())
                    .build())
                .collect(Collectors.toList());

            projectJoinUrlRepository.saveAll(projectJoinUrls);

            List<ProjectJoinFile> projectJoinFiles = applyProjectRequestDto.getFiles().stream()
                .map(fileDto -> ProjectJoinFile.builder()
                    .file(fileDto.getFileUrl())
                    .applicationId(application.getApplicationId())
                    .build())
                .collect(Collectors.toList());

            projectJoinFileRepository.saveAll(projectJoinFiles);

            List<ProjectJoinAnswer> projectJoinAnswers = applyProjectRequestDto.getAnswers().stream()
                .map(answerDto -> ProjectJoinAnswer.builder()
                    .answerText(answerDto.getAnswer())
                    .questionId((long) answerDto.getQuestionId())
                    .applicationId(application.getApplicationId())
                    .build())
                .collect(Collectors.toList());

            projectJoinAnswerRepository.saveAll(projectJoinAnswers);

            ApplyProjectResponseDto successResponseDto = ApplyProjectResponseDto.entityToDto(application);
            return successResponseDto;
        }else{
            throw new BaseException(BaseResponseStatus.RECRUITING_ALREADY_ENDED);
        }
    }
}
