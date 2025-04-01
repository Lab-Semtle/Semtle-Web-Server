package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Application;
import java.time.LocalDate;
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
    private List<ApplicationInfo> applications;

    @Getter
    @NoArgsConstructor
    public static class ApplicationInfo {
        private Long applicationId;
        private String projectTitle;
        private Long postId;
        private LocalDate applyDate;
        private String status;
        private String projectType;
        private String relateField;

        @Builder
        public ApplicationInfo(
            Long applicationId,
            String projectTitle,
            Long postId,
            LocalDate applyDate,
            String status,
            String projectType,
            String relateField) {
            this.applicationId = applicationId;
            this.projectTitle = projectTitle;
            this.postId = postId;
            this.applyDate = applyDate;
            this.status = status;
            this.projectType = projectType;
            this.relateField = relateField;
        }
    }


    public static ShowApplyingProjectInfoResponseDto entityToDto(Page<Application> applicationsPage, int currentPage) {
        List<Application> applications = applicationsPage.getContent();
        int totalElements = (int) applicationsPage.getTotalElements();

        List<ApplicationInfo> applicationInfoList = applications.stream()
            .map(application -> ApplicationInfo.builder()
                .applicationId(application.getApplicationId())
                .projectTitle(application.getProjectTitle())
                .postId(application.getPostId())
                .applyDate(application.getApplyDate())
                .status(application.getStatus())
                .projectType(application.getProjectType())
                .relateField(application.getRelateField())
                .build())
            .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / applications.size());

        return ShowApplyingProjectInfoResponseDto.builder()
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .applications(applicationInfoList)
            .build();
    }
}
