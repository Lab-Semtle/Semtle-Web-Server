package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Application;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private Integer applicationId;
        private String projectTitle;
        private Integer boardId;
        private Date applyDate;
        private String status;
        private String projectType;
        private String relateField;

        @Builder
        public ApplicationInfo(
            Integer applicationId,
            String projectTitle,
            Integer boardId,
            Date applyDate,
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


    public static ShowApplyingProjectInfoResponseDto entityToDto(List<Application> applications, int currentPage, int totalElements) {
        List<ApplicationInfo> applicationInfoList = applications.stream()
            .map(application -> ApplicationInfo.builder()
                .applicationId(application.getApplicationId())
                .projectTitle(application.getProjectTitle())
                .boardId(application.getBoardId())
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
