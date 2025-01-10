package com.archisemtle.semtlewebserverspring.application;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_DATA;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.NO_EXIST_CATEGORY;

import com.archisemtle.semtlewebserverspring.common.BaseException;
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

    @Override
    public ProjectBoardResponseDto getProjectBoard(Long id) {
//        ProjectBoard projectBoard = projectBoardRepository.findById(id)
//            .orElseThrow(() -> new BaseException(NO_DATA));
//
//        List<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddleList = relationFieldProjectPostMiddleRepository.findByProjectBoardId(
//            id);
//
//        if (relationFieldProjectPostMiddleList.isEmpty()) {
//            throw new BaseException(NO_EXIST_CATEGORY);
//        }
//
//        ProjectBoardResponseDto.builder()
//            .title(projectBoard.getTitle())
//            .content(projectBoard.getContent())
//            .writerName(projectBoard.getWriterName())
//            .projectTypeCategory(relationFieldProjectPostMiddleList)
        return null;
    }

    @Override
    public void getProjectBoardList() {

    }

    @Override
    public void deleteProjectBoard() {

    }

    @Override
    public void updateProjectBoard() {

    }
}
