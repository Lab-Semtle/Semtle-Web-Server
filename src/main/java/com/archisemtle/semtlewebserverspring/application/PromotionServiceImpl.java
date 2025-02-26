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
    private final RelationFieldProjectPromotionMiddleRepository relationFieldProjectPromotionMiddleRepository;
    private final ProjectPromotionImageRepository projectPromotionImageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PromotionResponseDto getPromotions(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // Spring은 0부터 시작하므로 -1
        Page<ProjectPromotion> projectBoardPage = promotionRepository.findByKeyword(keyword, pageable);

        // ProjectBoard -> PromotionResponseDto 변환
        List<ProjectPromotionResponseDto2> projectPromotionDtos = projectBoardPage.getContent().stream()
                .map(this::convertToProjectBoardResponseDto)
                .collect(Collectors.toList());


        return new PromotionResponseDto(
                projectBoardPage.getTotalElements(),  // totalItems (총 데이터 개수)
                page,                                // currentPage (현재 페이지)
                projectBoardPage.getTotalPages(),    // totalPage (전체 페이지 개수)
                projectPromotionDtos                     // projects (현재 페이지의 데이터 목록)
        );
    }

    @Override
    public ProjectPromotionResponseDto2 getPromotionsById(Long id) {
        ProjectPromotion projectPromotion = promotionRepository.findOneById(id);
        return projectPromotion == null ? null:convertToProjectBoardResponseDto(projectPromotion);
    }

    @Override
    public Boolean checkPermission(String uuid, Long id) {
        ProjectPromotion projectPromotion = promotionRepository.findOneByIdAndWriterUuid(uuid, id);
        return projectPromotion == null ? false:true;
    }

    @Override
    @Transactional
    public ProjectPromotionCUDResponseDto mergePromotion(ProjectPromotionRequestDto reqDto) {

        ProjectTypeCategory category = projectTypeCategoryRepository.getProjectTypeCategoryByName(reqDto.getProjectType());

        Date startDate = ConvertUtils.parseDate(reqDto.getCreateDate());
        Date dueDate = ConvertUtils.parseDate(reqDto.getDueDate());
        Date recruitingEndDate = ConvertUtils.parseDate(reqDto.getRecruitingEndTime());

        ProjectPromotion projectPromotion;

        // 🛠️ reqDto.getBoardId()가 null이면 새로운 ID 생성
        if (reqDto.getBoardId() == null) {
            projectPromotion = ProjectPromotion.builder()
                    .title(reqDto.getTitle())
                    .content(reqDto.getContents())
                    .writerUuid(reqDto.getUserUuid())
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

            entityManager.persist(projectPromotion);  // 신규 엔티티 저장
            entityManager.flush();  // DB에 반영하여 ID 생성
        } else {
            projectPromotion = ProjectPromotion.builder()
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

        ProjectPromotion mergedProject  = entityManager.merge(projectPromotion);
        entityManager.flush();
        entityManager.refresh(mergedProject);

        //relate_field 처리
        for(String relateFiled : reqDto.getRelateField()){
            //카테고리 이름을 통하여 카테고리를 가져옴
            RelationFieldCategory relationFieldCategory = relationFieldCategoryRepository.findOneByName(relateFiled);
            // 중간 테이블에서 해당 board와 relationFieldCategory의 연관 관계가 존재하는지 확인
            Optional<RelationFieldProjectPromotionPostMiddle> optionalMiddle  =
                    relationFieldProjectPromotionMiddleRepository.findByProjectPromotionAndRelationFieldCategory(mergedProject, relationFieldCategory);
            if(optionalMiddle.isEmpty()){
                // 해당 연관 관계가 없으면 새로 생성
                RelationFieldProjectPromotionPostMiddle newMiddle = RelationFieldProjectPromotionPostMiddle.builder()
                        .projectPromotion(mergedProject)
                        .relationFieldCategory(relationFieldCategory)
                        .build();
                entityManager.merge(newMiddle);
            }else{
                // 객체를 꺼내서 merge 호출
                RelationFieldProjectPromotionPostMiddle existingMiddle = optionalMiddle.get();
                entityManager.merge(existingMiddle);
            }
        }

        //image_url 처리
        for(String originalImgUrl : reqDto.getImageUrl()){
            String imgUrl = ConvertUtils.changeHTMLToString(originalImgUrl);
            ProjectPromotionImage existingImage = projectPromotionImageRepository.findOneByProjectPromotion(mergedProject);

            if(existingImage == null) {
                // 이미지가 없으면 새 ProjectImage 엔티티 생성 및 merge(insert)
                ProjectPromotionImage newImage = ProjectPromotionImage.builder()
                        .projectPromotion(mergedProject)
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
    public ProjectPromotionCUDResponseDto deletePromotion(Long id) {
        ProjectPromotion projectPromotion = promotionRepository.findOneById(id);
        projectPromotion.setUseYn("N");
//        projectBoard.setUpdateDt(new Date()); // 현재 시각으로 updateDt 갱신


        ProjectPromotion updatedProject = promotionRepository.save(projectPromotion);
        return convertToProjectBoardCUDResponseDto(updatedProject);
    }

    private ProjectPromotionResponseDto2 convertToProjectBoardResponseDto(ProjectPromotion projectPromotion) {

        List<String> imageUrls = projectPromotion.getImages().stream()
                .map(image -> {
                    String url = image.getUrl();
                    if (url != null) {
                        return "<" + url + ">";
                    }
                    return null;
                })
                .filter(url -> url != null)
                .collect(Collectors.toList());


        String projectType = projectPromotion.getProjectTypeCategory().getName();

        List<String> relateFields = projectPromotion.getRelationFieldProjectPostMiddleList().stream()
                .map(rf -> rf.getName())
                .collect(Collectors.toList());

        String member = projectPromotion.getProjectMember() + "명";

        return new ProjectPromotionResponseDto2(
                projectPromotion.getId(),           // board_id
                projectPromotion.getTitle(),        //title
//                projectBoard.getSubTitle(),     //subtitle
                projectPromotion.getWriterName(),   //wrtier
                ConvertUtils.changeStringToHTML(projectPromotion.getProjectLink()),  //result_link
                imageUrls,     //image_url
                ConvertUtils.changeDateToString(projectPromotion.getCreateDt()),     //create_date
                ConvertUtils.changeDateToString(projectPromotion.getProjectEndTime()),   //due_date
                projectType,      //project_type
                relateFields,      //relate_field
                member,    //member
                projectPromotion.getContent()           //contents
        );
    }

    private ProjectPromotionCUDResponseDto convertToProjectBoardCUDResponseDto(ProjectPromotion projectPromotion) {

        return new ProjectPromotionCUDResponseDto(
                projectPromotion.getId(),
                ConvertUtils.changeFullDateToString(projectPromotion.getCreateDt()),
                ConvertUtils.changeFullDateToString(projectPromotion.getUpdateDt())
        );
    }

}
