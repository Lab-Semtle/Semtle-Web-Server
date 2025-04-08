package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Apply;
import com.archisemtle.semtlewebserverspring.domain.RelationFieldProjectPostMiddle;
import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldProjectPostMiddleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
public class ShowApplyingProjectInfoResponseDto {

    private int totalElements;
    private int totalPages;
    private int currentPage;
    private List<ApplyInfo> applys;

    @Getter
    @NoArgsConstructor
    public static class ApplyInfo {
        private Long applyId;
        private Long postId;
        private String projectTitle;
        private LocalDateTime applyDate;
        private String status;
        private String projectType;
        private String relateField;

        @Builder
        public ApplyInfo(
            Long applyId,
            Long postId,
            String projectTitle,
            String status,
            String projectType,
            String relateField,
            LocalDateTime applyDate) {
            this.applyId = applyId;
            this.postId = postId;
            this.projectTitle = projectTitle;
            this.status = status;
            this.projectType = projectType;
            this.relateField = relateField;
            this.applyDate = applyDate;
        }
    }


    public static ShowApplyingProjectInfoResponseDto entityToDto(Page<Apply> applyPage, int currentPage, RelationFieldProjectPostMiddleRepository relationFieldProjectPostMiddleRepository) {

        List<Apply> applys = applyPage.getContent();
        int totalElements = (int) applyPage.getTotalElements();

        List<ApplyInfo> applyInfoList = applys.stream()
            .map(apply -> {
                List<RelationFieldProjectPostMiddle> relatedFields = relationFieldProjectPostMiddleRepository
                    .findAllByProjectBoardId(apply.getProjectBoard().getId());

                String relatedFieldsStr = relatedFields.stream()
                    .map(field -> field.getRelationFieldCategory().getName())
                    .collect(Collectors.joining(", "));

                return ApplyInfo.builder()
                    .applyId(apply.getApplyId())
                    .postId(apply.getProjectBoard().getId())
                    .projectTitle(apply.getProjectBoard().getTitle())
                    .status(apply.getStatus())
                    .projectType(apply.getProjectBoard().getProjectTypeCategory().getName())
                    .relateField(relatedFieldsStr)
                    .applyDate(apply.getApplyDate())
                    .build();
            })
            .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / applys.size());

        return ShowApplyingProjectInfoResponseDto.builder()
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .applys(applyInfoList)
            .build();
    }
}
