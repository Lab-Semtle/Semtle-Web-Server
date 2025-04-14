package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.Apply;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.ProjectJoinFile;
import com.archisemtle.semtlewebserverspring.domain.ProjectJoinUrl;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ApplyProjectResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ApplyRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectJoinFileRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectJoinUrlRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyProjectServiceImpl implements ApplyProjectService {

    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;
    private final ProjectBoardRepository projectBoardRepository;
    private final ProjectJoinFileRepository projectJoinFileRepository;
    private final ProjectJoinUrlRepository projectJoinUrlRepository;

    @Override
    @Transactional
    public ApplyProjectResponseDto applyProject(ApplyProjectRequestDto applyProjectRequestDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID uuid = UUID.fromString(authentication.getName());

        if(applyProjectRequestDto == null) {
            throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        }

        ProjectBoard projectBoard = projectBoardRepository.findById(applyProjectRequestDto.getPostId())
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_BOARD_FOUND));

        if(LocalDate.now().isBefore(projectBoard.getProjectRecruitingEndTime().plusDays(1))) {
            Member member = memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new BaseException(
                    BaseResponseStatus.NO_EXIST_MEMBERS));

            if(projectBoard.getWriterUuid().equals(member.getUuid().toString())) {
                throw new BaseException(BaseResponseStatus.SAME_WRITER);
            }


            Apply existingApply = applyRepository.findByMemberAndProjectBoardId(member, applyProjectRequestDto.getPostId());

            if (existingApply != null) {
                throw new BaseException(BaseResponseStatus.DUPLICATE_APPLY);
            }

            Apply apply = Apply.builder()
                .member(member)
                .projectBoard(projectBoard)
                .applyDate(LocalDateTime.now())
                .status("대기")
                .updatedAt(LocalDateTime.now())
                .answer(applyProjectRequestDto.getAnswer())
                .build();

            applyRepository.save(apply);

            List<ProjectJoinUrl> projectJoinUrls = applyProjectRequestDto.getUrls().stream()
                .map(url -> ProjectJoinUrl.builder()
                    .url(url)
                    .apply(apply)
                    .build())
                .collect(Collectors.toList());

            projectJoinUrlRepository.saveAll(projectJoinUrls);

            List<ProjectJoinFile> projectJoinFiles = applyProjectRequestDto.getFileUrls().stream()
                .map(fileUrl -> ProjectJoinFile.builder()
                    .file(fileUrl)
                    .apply(apply)
                    .build())
                .collect(Collectors.toList());

            projectJoinFileRepository.saveAll(projectJoinFiles);


            ApplyProjectResponseDto applyProjectResponseDto = ApplyProjectResponseDto.entityToDto(apply);
            return applyProjectResponseDto;
        }else{
            throw new BaseException(BaseResponseStatus.RECRUITING_ALREADY_ENDED);
        }
    }
}
