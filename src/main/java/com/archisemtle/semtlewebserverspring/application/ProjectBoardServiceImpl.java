package com.archisemtle.semtlewebserverspring.application;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.FAIL_TO_DELETE;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.FAIL_TO_UPDATE;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_DATA;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_EXIST_CATEGORY;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_EXIST_MEMBERS;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoardImage;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import com.archisemtle.semtlewebserverspring.dto.AddProjectBoardRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardPageResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectListRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateProjectBoardRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardImageRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldCategoryRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldProjectPostMiddleRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBoardServiceImpl implements ProjectBoardService {

    private final ProjectBoardRepository projectBoardRepository;
    private final RelationFieldProjectPostMiddleRepository relationFieldProjectPostMiddleRepository;
    private final RelationFieldCategoryRepository relationFieldCategoryRepository;
    private final MemberRepository memberRepository;
    private final ProjectBoardImageRepository projectBoardImageRepository;

    //게시물작성
    @Override
    @Transactional
    public void addProjectBoard(UUID uuid, AddProjectBoardRequestDto addProjectBoardRequestDto) {

        Member member = memberRepository.findByUuid(uuid)
            .orElseThrow(() -> new BaseException(NO_EXIST_MEMBERS));

        ProjectBoard projectBoard = ProjectBoard.builder()
            .title(addProjectBoardRequestDto.getTitle())
            .content(addProjectBoardRequestDto.getContent())
            .writerUuid(uuid.toString())
            .writerName(member.getUsername())
            .contact(addProjectBoardRequestDto.getContact())
            .projectTypeCategory(addProjectBoardRequestDto.getProjectTypeCategory())
            .projectStartTime(addProjectBoardRequestDto.getProjectStartTime())
            .projectEndTime(addProjectBoardRequestDto.getProjectEndTime())
            .projectRecruitingEndTime(addProjectBoardRequestDto.getProjectRecruitingEndTime())
            .projectStatus(ProjectStatus.RECRUITING)
            .build();

        projectBoardRepository.save(projectBoard);

        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddles = addProjectBoardRequestDto.getRelationFieldCategories()
            .stream().map(
                relationFieldCategory -> {
                    RelationFieldCategory validationRelationFieldCategory = relationFieldCategoryRepository.findById(
                            relationFieldCategory.getId())
                        .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));
                    RelationFieldProjectPostMiddle relationFieldProjectPostMiddle = RelationFieldProjectPostMiddle.builder()
                        .projectBoard(projectBoard)
                        .relationFieldCategory(validationRelationFieldCategory)
                        .build();
                    return relationFieldProjectPostMiddle;
                })
            .collect(Collectors.toList());

        relationFieldProjectPostMiddleRepository.saveAll(relationFieldProjectPostMiddles);

        List<ProjectBoardImage> projectBoardImages = addProjectBoardRequestDto.getProjectBoardImages()
            .stream().map(
                image -> ProjectBoardImage.builder()
                    .projectBoard(projectBoard)
                    .projectBoardImageUrl(image)
                    .build())
            .collect(Collectors.toList());

        projectBoardImageRepository.saveAll(projectBoardImages);
    }

    //게시물 한개 조회
    @Override
    public ProjectBoardResponseDto getProjectBoard(Long id) {
        ProjectBoard projectBoard = projectBoardRepository.findById(id)
            .orElseThrow(() -> new BaseException(NO_DATA)); //todo 나중에 BaseResponseStatue 수정 필요

        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddleList = relationFieldProjectPostMiddleRepository.findAllByProjectBoardId(
            id);

        if (relationFieldProjectPostMiddleList.isEmpty()) {
            throw new BaseException(NO_EXIST_CATEGORY);
        }

        List<String> relationFieldCategoryNames = relationFieldProjectPostMiddleList.stream()
            .map(
                relationFieldProjectPostMiddle -> relationFieldProjectPostMiddle.getRelationFieldCategory()
                    .getName())
            .toList();

        List<String> projectBoardImages = projectBoardImageRepository.findAllByProjectBoardId(
                id).stream().map(ProjectBoardImage::getProjectBoardImageUrl)
            .collect(Collectors.toList());

        return ProjectBoardResponseDto.builder()
            .title(projectBoard.getTitle())
            .content(projectBoard.getContent())
            .writerName(projectBoard.getWriterName())
            .contact(projectBoard.getContact())
            .projectTypeCategoryName(projectBoard.getProjectTypeCategory().getName())
            .relationFieldCategoryName(relationFieldCategoryNames)
            .projectStartTime(projectBoard.getProjectStartTime())
            .projectEndTime(projectBoard.getProjectEndTime())
            .projectRecruitingEndTime(projectBoard.getProjectRecruitingEndTime())
            .projectStatus(projectBoard.getProjectStatus())
            .projectBoardImages(projectBoardImages)
            .build();
    }


    @Override
    @Transactional
    public Page<ProjectListRequestDto> getProjectBoardList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

//        ProjectBoard test = projectBoardRepository.findById(1L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        ProjectBoard test2 = projectBoardRepository.findById(4L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        ProjectBoard projectBoard = ProjectBoard.builder()
//            .id(test.getId())
//            .title(test.getTitle())
//            .content(test.getContent())
//            .contact(test.getContact())
//            .projectEndTime(test.getProjectEndTime())
//            .projectRecruitingEndTime(test.getProjectRecruitingEndTime())
//            .projectStartTime(test.getProjectStartTime())
//            .projectTypeCategory(test.getProjectTypeCategory())
//            .projectStatus(ProjectStatus.IN_PROGRESS)
//            .writerName(test.getWriterName())
//            .writerUuid(test.getWriterUuid())
//            .build();
//
//        ProjectBoard projectBoardInsert2 = ProjectBoard.builder()
//            .id(test2.getId())
//            .title(test2.getTitle())
//            .content(test2.getContent())
//            .contact(test2.getContact())
//            .projectEndTime(test2.getProjectEndTime())
//            .projectRecruitingEndTime(test2.getProjectRecruitingEndTime())
//            .projectStartTime(test2.getProjectStartTime())
//            .projectTypeCategory(test2.getProjectTypeCategory())
//            .projectStatus(ProjectStatus.IN_PROGRESS)
//            .writerName(test2.getWriterName())
//            .writerUuid(test2.getWriterUuid())
//            .build();
//
//        projectBoardRepository.save(projectBoard);
//        projectBoardRepository.save(projectBoardInsert2);
//
//        ProjectBoard projectBoard1 = projectBoardRepository.findById(1L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        ProjectBoard projectBoard2 = projectBoardRepository.findById(2L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        ProjectBoard projectBoard3 = projectBoardRepository.findById(2L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        ProjectBoard projectBoard4 = projectBoardRepository.findById(4L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        ProjectBoard projectBoard5 = projectBoardRepository.findById(2L)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        System.out.println(projectBoard1.getProjectStatus());
//        System.out.println(projectBoard2.getProjectStatus());
//        System.out.println(projectBoard3.getProjectStatus());
//        System.out.println(projectBoard4.getProjectStatus());
//        System.out.println(projectBoard5.getProjectStatus());

        Page<ProjectBoard> projectBoardList = projectBoardRepository.findAllOrderByCustomStatus(
            pageable);

        return projectBoardList.map(board -> ProjectListRequestDto.builder()
            .projectBoardId(board.getId())
            .build());
    }

    //게시물 한개의 리스트에서의 조회
    @Override
    public ProjectBoardPageResponseDto getProjectBoardPage(Long id) {
        ProjectBoard projectBoard = projectBoardRepository.findById(id)
            .orElseThrow(() -> new BaseException(NO_DATA)); //todo 나중에 BaseResponseStatue 수정 필요

        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddleList = relationFieldProjectPostMiddleRepository.findAllByProjectBoardId(
            id);

        if (relationFieldProjectPostMiddleList.isEmpty()) {
            throw new BaseException(NO_EXIST_CATEGORY);
        }

        List<String> relationFieldCategoryNames = relationFieldProjectPostMiddleList.stream()
            .map(
                relationFieldProjectPostMiddle -> relationFieldProjectPostMiddle.getRelationFieldCategory()
                    .getName())
            .toList();

        return ProjectBoardPageResponseDto.builder()
            .title(projectBoard.getTitle())
            .writerName(projectBoard.getWriterName())
            .projectTypeCategoryName(projectBoard.getProjectTypeCategory().getName())
            .relationFieldCategoryName(relationFieldCategoryNames)
            .projectRecruitingEndTime(projectBoard.getProjectRecruitingEndTime())
            .projectStatus(projectBoard.getProjectStatus())
            .build();
    }

    @Override
    @Transactional
    public void deleteProjectBoard(UUID uuid, Long id) {

        ProjectBoard projectBoard = projectBoardRepository.findById(id)
            .orElseThrow(() -> new BaseException(NO_DATA));

        if (!uuid.toString().equals(projectBoard.getWriterUuid())) {
            throw new BaseException(FAIL_TO_DELETE);
        }

        List<ProjectBoardImage> projectBoardImages = projectBoardImageRepository.findAllByProjectBoardId(
            id);

        if (!projectBoardImages.isEmpty()) {
            projectBoardImageRepository.deleteAllByProjectBoardId(id);
        }

        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddleList = relationFieldProjectPostMiddleRepository.findAllByProjectBoardId(
            id);

        if (relationFieldProjectPostMiddleList.isEmpty()) {
            throw new BaseException(NO_DATA);
        }

        relationFieldProjectPostMiddleRepository.deleteAllByProjectBoardId(id);
        projectBoardRepository.delete(projectBoard);
    }

    @Override
    @Transactional
    public void updateProjectBoard(UUID uuid, Long id,
        UpdateProjectBoardRequestDto updateProjectBoardRequestDto) {
        ProjectBoard origin = projectBoardRepository.findById(id)
            .orElseThrow(() -> new BaseException(NO_DATA));

        if (!uuid.toString().equals(origin.getWriterUuid())) {
            throw new BaseException(FAIL_TO_UPDATE);
        }

        ProjectBoard projectBoard = ProjectBoard.builder()
            .id(id)
            .title(updateProjectBoardRequestDto.getTitle())
            .content(updateProjectBoardRequestDto.getContent())
            .writerUuid(origin.getWriterUuid())
            .writerName(origin.getWriterName())
            .contact(updateProjectBoardRequestDto.getContact())
            .projectTypeCategory(updateProjectBoardRequestDto.getProjectTypeCategory())
            .projectStartTime(updateProjectBoardRequestDto.getProjectStartTime())
            .projectEndTime(updateProjectBoardRequestDto.getProjectEndTime())
            .projectRecruitingEndTime(updateProjectBoardRequestDto.getProjectRecruitingEndTime())
            .projectStatus(origin.getProjectStatus())
            .build();

        projectBoardRepository.save(projectBoard);

        List<ProjectBoardImage> projectBoardImages = projectBoardImageRepository.findAllByProjectBoardId(
            id);

        if (!projectBoardImages.isEmpty()) {
            projectBoardImageRepository.deleteAllByProjectBoardId(id);
        }
        List<ProjectBoardImage> projectBoardImageList = updateProjectBoardRequestDto.getProjectBoardImages()
            .stream().map(
                image -> ProjectBoardImage.builder()
                    .projectBoard(projectBoard)
                    .projectBoardImageUrl(image)
                    .build())
            .collect(Collectors.toList());
        projectBoardImageRepository.saveAll(projectBoardImageList);

        relationFieldProjectPostMiddleRepository.deleteAllByProjectBoardId(id);

        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddles = updateProjectBoardRequestDto.getRelationFieldCategories()
            .stream().map(
                relationFieldCategory -> {
                    RelationFieldCategory validationRelationFieldCategory = relationFieldCategoryRepository.findById(
                            relationFieldCategory.getId())
                        .orElseThrow(() -> new BaseException(NO_EXIST_CATEGORY));
                    RelationFieldProjectPostMiddle relationFieldProjectPostMiddle = RelationFieldProjectPostMiddle.builder()
                        .projectBoard(projectBoard)
                        .relationFieldCategory(validationRelationFieldCategory)
                        .build();
                    return relationFieldProjectPostMiddle;
                })
            .collect(Collectors.toList());

        relationFieldProjectPostMiddleRepository.saveAll(relationFieldProjectPostMiddles);
    }
}
