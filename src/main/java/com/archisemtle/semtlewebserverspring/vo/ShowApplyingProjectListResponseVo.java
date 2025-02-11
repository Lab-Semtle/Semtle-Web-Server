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

    private long totalElements;
    private int totalPages;
    private int currentPage;
    private List<ApplicationInfo> applications;

    @Getter
    @NoArgsConstructor
    public static class ApplicationInfo {
        private Long applicationId;
        private String projectTitle;
        private Long boardId;
        private LocalDateTime applyDate;
        private String status;
        private String projectType;
        private String relateField;

        @Builder
        public ApplicationInfo(
            Long applicationId,
            String projectTitle,
            Long boardId,
            LocalDateTime applyDate,
            String status,
            String projectType,
            String relateField) {
            this.applicationId = applicationId;
            this.projectTitle = projectTitle;
            this.boardId = boardId;
            this.applyDate = applyDate;
            this.status = status;
            this.projectType = projectType;
            this.relateField = relateField;
        }
    }

    @Builder
    public ShowApplyingProjectListResponseVo(long totalElements, int totalPages, int currentPage,
        List<ApplicationInfo> applications) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.applications = applications;
    }

    public static ShowApplyingProjectListResponseVo dtoToVo(
        ShowApplyingProjectInfoResponseDto showApplyingProjectInfoResponseDto) {

        List<ApplicationInfo> applicationInfoList = showApplyingProjectInfoResponseDto.getApplications().stream()
            .map(dtoApplication -> ShowApplyingProjectListResponseVo.ApplicationInfo.builder()
                .applicationId(dtoApplication.getApplicationId())
                .projectTitle(dtoApplication.getProjectTitle())
                .boardId(dtoApplication.getBoardId())
                .applyDate(dtoApplication.getApplyDate())
                .status(dtoApplication.getStatus())
                .projectType(dtoApplication.getProjectType())
                .relateField(dtoApplication.getRelateField())
                .build())
            .collect(Collectors.toList());

        return ShowApplyingProjectListResponseVo.builder()
            .totalElements(showApplyingProjectInfoResponseDto.getTotalElements())
            .totalPages(showApplyingProjectInfoResponseDto.getTotalPages())
            .currentPage(showApplyingProjectInfoResponseDto.getCurrentPage())
            .applications(applicationInfoList)
            .build();
    }
}
