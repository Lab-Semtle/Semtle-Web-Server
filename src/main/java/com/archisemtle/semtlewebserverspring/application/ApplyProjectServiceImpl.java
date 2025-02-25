package com.archisemtle.semtlewebserverspring.application;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_DATA;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Applicants;
import com.archisemtle.semtlewebserverspring.domain.Application;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.ProjectTypeCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto.FileDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicantsRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicationRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldProjectPostMiddleRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    @Override
    @Transactional
    public ApplyProjectResponseDto applyProject(Integer boardId,Integer applicantId, ApplyProjectRequestDto applyProjectRequestDto)
        throws Exception {
        ProjectBoard projectBoard = projectBoardRepository.findById(Long.valueOf(boardId))
            .orElseThrow(() -> new BaseException(NO_DATA)); //todo 나중에 BaseResponseStatue 수정 필요

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime endDateTime = new Timestamp(projectBoard.getProjectRecruitingEndTime().getTime()).toLocalDateTime();

        if(now.isBefore(endDateTime)) {
            Member member = memberRepository.findById(applicantId)
                .orElseThrow(() -> new BaseException(
                    BaseResponseStatus.NO_EXIST_MEMBERS));

            Date applyDate = Date.from(
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

            String updatedAt = LocalDateTime.now()
                .atZone(ZoneId.of("UTC"))
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

            Applicants applicants = Applicants.builder()
                .name(member.getName())
                .applyDate(applyDate)
                .status("대기") // 기본값
                .email(member.getEmail())
                .phone(member.getPhone())
                .resumeUrl(applyProjectRequestDto.getFiles().stream()
                    .filter(file -> file.getFileName().equals("resume.pdf"))
                    .map(FileDto::getFileUrl)
                    .findFirst()
                    .orElse(null))
                .portfolioUrl(applyProjectRequestDto.getUrls().stream()
                    .findFirst()
                    .orElse(null))
                .customAnswer("답변 내용")
                .updatedAt(updatedAt)
                .boardId(boardId)
                .build();

            applicantsRepository.save(applicants);

            ProjectTypeCategory projectTypeName = projectBoard.getProjectTypeCategory(); // project_type_category_name 가져오기

            List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddles = relationFieldProjectPostMiddleRepository.findAllByProjectBoardId(Long.valueOf(boardId));

            List<String> relationFieldNames = relationFieldProjectPostMiddles.stream()
                .map(relationField -> relationField.getRelationFieldCategory().getName()) // 이름 가져오기
                .collect(Collectors.toList());

            String questionAnswers = applyProjectRequestDto.getAnswers().stream()
                .map(ApplyProjectRequestDto.AnswerDto::getAnswer) // answer만 추출
                .collect(Collectors.joining("*|*")); // 구분자를 사용하여 하나의 문자열로 변환
            // remerge
            Application application = Application.builder()
                .applicantId(applicants.getApplicantId())
                .projectTitle(projectBoard.getTitle())
                .boardId(boardId)
                .applyDate(applyDate)
                .status("대기") // 수정됨.
                .projectType(projectTypeName.getName())
                .relateField(relationFieldNames.toString())
                .questionAnswer(questionAnswers)
                .build();

            applicationRepository.save(application);

            // 응답 DTO 생성
            ApplyProjectResponseDto successResponseDto = ApplyProjectResponseDto.entityToDto(application);

            return successResponseDto;
        }else{
            ApplyProjectResponseDto failResponseDto = ApplyProjectResponseDto.entityToDto(null);

            return failResponseDto;
        }
    }
}
