package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ShowApplyingProjectInfoResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowApplyingProjectListResponseVo {

    private int totalElements;
    private int totalPages;
    private int currentPage;
    private List<ApplyInfo> apply;

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


    @Builder
    public ShowApplyingProjectListResponseVo(int totalElements, int totalPages, int currentPage,
        List<ApplyInfo> apply) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.apply = apply;
    }

    public static ShowApplyingProjectListResponseVo dtoToVo(
        ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto) {

        List<ApplyInfo> applyInfoList = showApplyingProjectInfoResponseDto.getApplys().stream()
            .map(apply -> ShowApplyingProjectListResponseVo.ApplyInfo.builder()
                .applyId(apply.getApplyId())
                .postId(apply.getPostId())
                .projectTitle(apply.getProjectTitle())
                .status(apply.getStatus())
                .projectType(apply.getProjectType())
                .relateField(apply.getRelateField())
                .applyDate(apply.getApplyDate())
                .build())
            .collect(Collectors.toList());

        return ShowApplyingProjectListResponseVo.builder()
            .totalElements(showApplyingProjectInfoResponseDto.getTotalElements())
            .totalPages(showApplyingProjectInfoResponseDto.getTotalPages())
            .currentPage(showApplyingProjectInfoResponseDto.getCurrentPage())
            .apply(applyInfoList)
            .build();
    }
}
