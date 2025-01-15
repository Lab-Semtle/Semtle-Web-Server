package com.archisemtle.semtlewebserverspring.application;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_DATA;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_EXIST_CATEGORY;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldCategory;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import com.archisemtle.semtlewebserverspring.dto.AddProjectBoardRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldCategoryRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldProjectPostMiddleRepository;
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
public class ProjectBoardServiceImpl implements ProjectBoardService {

    private final ProjectBoardRepository projectBoardRepository;
    private final RelationFieldProjectPostMiddleRepository relationFieldProjectPostMiddleRepository;
    private final RelationFieldCategoryRepository relationFieldCategoryRepository;

    //게시물작성
    @Override
    @Transactional
    public void addProjectBoard(AddProjectBoardRequestDto addProjectBoardRequestDto) {
        ProjectBoard projectBoard = ProjectBoard.builder()
            .title(addProjectBoardRequestDto.getTitle())
            .content(addProjectBoardRequestDto.getContent())
            .writerUuid("temp") //TODO: 나중에 실제 값으로 변경해야함
            .writerName("tempName")
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

        return ProjectBoardResponseDto.builder()
            .title(projectBoard.getTitle())
            .content(projectBoard.getContent())
            .writerName(projectBoard.getWriterName())
            .projectTypeCategoryName(projectBoard.getProjectTypeCategory().getName())
            .relationFieldCategoryName(relationFieldCategoryNames)
            .projectStartTime(projectBoard.getProjectStartTime())
            .projectEndTime(projectBoard.getProjectEndTime())
            .projectRecruitingEndTime(projectBoard.getProjectRecruitingEndTime())
            .projectStatus(projectBoard.getProjectStatus())
            .build();
    }

    @Override
    public void getProjectBoardList() {

    }

    @Override
    @Transactional
    public void deleteProjectBoard(Long id) {
        ProjectBoard projectBoard = projectBoardRepository.findById(id)
            .orElseThrow(() -> new BaseException(NO_DATA));

        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddleList = relationFieldProjectPostMiddleRepository.findAllByProjectBoardId(
            id);

        if (relationFieldProjectPostMiddleList.isEmpty()) {
            throw new BaseException(NO_DATA);
        }

        relationFieldProjectPostMiddleRepository.deleteAllByProjectBoardId(id);
        projectBoardRepository.delete(projectBoard);
    }

    @Override
    public void updateProjectBoard() {

    }
}
