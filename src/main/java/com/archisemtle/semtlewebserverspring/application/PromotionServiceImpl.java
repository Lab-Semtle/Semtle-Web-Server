package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.common.utils.ConvertUtils;
import com.archisemtle.semtlewebserverspring.domain.*;
import com.archisemtle.semtlewebserverspring.dto.*;
import com.archisemtle.semtlewebserverspring.infrastructure.*;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ProjectTypeCategoryRepository projectTypeCategoryRepository;
    private final RelationFieldCategoryRepository relationFieldCategoryRepository;
    private final RelationFieldProjectPostMiddleRepository relationFieldProjectPostMiddleRepository;
    private final ProjectImageRepository projectImageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PromotionResponseDto getPromotions(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // Spring은 0부터 시작하므로 -1
        Page<ProjectBoard> projectBoardPage = promotionRepository.findByKeyword(keyword, pageable);

        // ProjectBoard -> PromotionResponseDto 변환
        List<ProjectBoardResponseDto2> projectBoardDtos = projectBoardPage.getContent().stream()
                .map(this::convertToProjectBoardResponseDto)
                .collect(Collectors.toList());


        return new PromotionResponseDto(
                projectBoardPage.getTotalElements(),  // totalItems (총 데이터 개수)
                page,                                // currentPage (현재 페이지)
                projectBoardPage.getTotalPages(),    // totalPage (전체 페이지 개수)
                projectBoardDtos                     // projects (현재 페이지의 데이터 목록)
        );
    }

    @Override
    public ProjectBoardResponseDto2 getPromotionsById(Long id) {
        ProjectBoard projectBoard = promotionRepository.findOneById(id);
        return projectBoard == null ? null:convertToProjectBoardResponseDto(projectBoard);
    }

    @Override
    @Transactional
    public ProjectBoardCUDResponseDto mergePromotion(ProjectBoardRequestDto reqDto) {

        ProjectTypeCategory category = projectTypeCategoryRepository.getProjectTypeCategoryByName(reqDto.getProjectType());

        Date startDate = ConvertUtils.parseDate(reqDto.getCreateDate());
        Date dueDate = ConvertUtils.parseDate(reqDto.getDueDate());
        Date recruitingEndDate = ConvertUtils.parseDate(reqDto.getRecruitingEndTime());

        ProjectBoard projectBoard;

        // 🛠️ reqDto.getBoardId()가 null이면 새로운 ID 생성
        if (reqDto.getBoardId() == null) {
            projectBoard = ProjectBoard.builder()
                    .title(reqDto.getTitle())
                    .content(reqDto.getContents())
                    .writerUuid("UUID-PLACEHOLDER")
                    .writerName(reqDto.getWriter())
                    .contact(null)
                    .projectTypeCategory(category)
                    .projectStartTime(startDate)
                    .projectEndTime(dueDate)
                    .projectRecruitingEndTime(recruitingEndDate)
                    .projectStatus(ProjectStatus.RECRUITING)
                    .useYn("Y")
                    .projectLink(ConvertUtils.changeHTMLToString(reqDto.getResultLink()))
                    .projectMember(ConvertUtils.getNumber(reqDto.getMember()))
                    .build();

            entityManager.persist(projectBoard);  // 신규 엔티티 저장
            entityManager.flush();  // DB에 반영하여 ID 생성
        } else {
            projectBoard = ProjectBoard.builder()
                    .id(reqDto.getBoardId()) // 기존 ID 사용
                    .title(reqDto.getTitle())
                    .content(reqDto.getContents())
                    .writerUuid("UUID-PLACEHOLDER")
                    .writerName(reqDto.getWriter())
                    .contact(null)
                    .projectTypeCategory(category)
                    .projectStartTime(startDate)
                    .projectEndTime(dueDate)
                    .projectRecruitingEndTime(recruitingEndDate)
                    .projectStatus(ProjectStatus.RECRUITING)
                    .useYn("Y")
                    .projectLink(ConvertUtils.changeHTMLToString(reqDto.getResultLink()))
                    .projectMember(ConvertUtils.getNumber(reqDto.getMember()))
                    .build();
        }

        ProjectBoard mergedProject  = entityManager.merge(projectBoard);
        entityManager.flush();
        entityManager.refresh(mergedProject);

        //relate_field 처리
        for(String relateFiled : reqDto.getRelateField()){
            //카테고리 이름을 통하여 카테고리를 가져옴
            RelationFieldCategory relationFieldCategory = relationFieldCategoryRepository.findOneByName(relateFiled);
            // 중간 테이블에서 해당 board와 relationFieldCategory의 연관 관계가 존재하는지 확인
            Optional<RelationFieldProjectPostMiddle> optionalMiddle  =
                    relationFieldProjectPostMiddleRepository.findByProjectBoardAndRelationFieldCategory(mergedProject, relationFieldCategory);
            if(optionalMiddle.isEmpty()){
                // 해당 연관 관계가 없으면 새로 생성
                RelationFieldProjectPostMiddle newMiddle = RelationFieldProjectPostMiddle.builder()
                        .projectBoard(mergedProject)
                        .relationFieldCategory(relationFieldCategory)
                        .build();
                entityManager.merge(newMiddle);
            }else{
                // 객체를 꺼내서 merge 호출
                RelationFieldProjectPostMiddle existingMiddle = optionalMiddle.get();
                entityManager.merge(existingMiddle);
            }
        }

        //image_url 처리
        for(String originalImgUrl : reqDto.getImageUrl()){
            String imgUrl = ConvertUtils.changeHTMLToString(originalImgUrl);
            ProjectImage existingImage = projectImageRepository.findOneByProjectBoard(mergedProject);

            if(existingImage == null) {
                // 이미지가 없으면 새 ProjectImage 엔티티 생성 및 merge(insert)
                ProjectImage newImage = ProjectImage.builder()
                        .projectBoard(mergedProject)
                        .url(imgUrl)
                        .build();
                entityManager.merge(newImage);
            } else {
                // 이미지가 이미 존재하면, URL 업데이트 후 merge(update)
                existingImage.setUrl(imgUrl);  // setUrl() 메서드가 있다고 가정
                entityManager.merge(existingImage);
            }
        }

        return convertToProjectBoardCUDResponseDto(mergedProject);
    }

    @Override
    public ProjectBoardCUDResponseDto deletePromotion(Long id) {
        ProjectBoard projectBoard = promotionRepository.findOneById(id);
        projectBoard.setUseYn("N");
//        projectBoard.setUpdateDt(new Date()); // 현재 시각으로 updateDt 갱신


        ProjectBoard updatedProject = promotionRepository.save(projectBoard);
        return convertToProjectBoardCUDResponseDto(updatedProject);
    }

    private ProjectBoardResponseDto2 convertToProjectBoardResponseDto(ProjectBoard projectBoard) {

        List<String> imageUrls = projectBoard.getImages().stream()
                .map(image -> {
                    String url = image.getUrl();
                    if (url != null) {
                        return "<" + url + ">";
                    }
                    return null;
                })
                .filter(url -> url != null)
                .collect(Collectors.toList());


        String projectType = projectBoard.getProjectTypeCategory().getName();

        List<String> relateFields = projectBoard.getRelationFieldProjectPostMiddleList().stream()
                .map(rf -> rf.getName())
                .collect(Collectors.toList());

        String member = projectBoard.getProjectMember() + "명";

        return new ProjectBoardResponseDto2(
                projectBoard.getId(),           // board_id
                projectBoard.getTitle(),        //title
//                projectBoard.getSubTitle(),     //subtitle
                projectBoard.getWriterName(),   //wrtier
                ConvertUtils.changeStringToHTML(projectBoard.getProjectLink()),  //result_link
                imageUrls,     //image_url
                ConvertUtils.changeDateToString(projectBoard.getCreateDt()),     //create_date
                ConvertUtils.changeDateToString(projectBoard.getProjectEndTime()),   //due_date
                projectType,      //project_type
                relateFields,      //relate_field
                member,    //member
                projectBoard.getContent()           //contents
        );
    }

    private ProjectBoardCUDResponseDto convertToProjectBoardCUDResponseDto(ProjectBoard projectBoard) {

        return new ProjectBoardCUDResponseDto(
                projectBoard.getId(),
                ConvertUtils.changeFullDateToString(projectBoard.getCreateDt()),
                ConvertUtils.changeFullDateToString(projectBoard.getUpdateDt())
        );
    }

}
