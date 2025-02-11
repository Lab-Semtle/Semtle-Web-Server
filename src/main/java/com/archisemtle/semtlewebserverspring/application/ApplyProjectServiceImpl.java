package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Applicants;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto.FileDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplicantsRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import java.time.LocalDateTime;
import java.util.Date;
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
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ApplyProjectResponseDto applyProject(int boardId,int applicantId, ApplyProjectRequestDto applyProjectRequestDto)
        throws Exception {
        Member member = memberRepository.findById(applicantId).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));

        Applicants applicants = Applicants.builder()
            .name(member.getName())
            .applyDate(new Date())
            .status("대기")
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
            .boardId(boardId)
            .build();

        Applicants savedApplicant = applicantsRepository.save(applicants);

        // 응답 DTO 생성
        ApplyProjectResponseDto responseDto = ApplyProjectResponseDto.builder()
            .message("지원이 완료되었습니다.")
            .appliedId(savedApplicant.getApplicantId())
            .appliedAt(String.valueOf(LocalDateTime.now()))
            .build();

        return responseDto;
    }
}
