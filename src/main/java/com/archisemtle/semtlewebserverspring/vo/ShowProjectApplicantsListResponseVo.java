package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowProjectApplicantsListResponseVo {

    private int totalElements;
    private int totalPages;
    private int currentPage;
    private List<ApplicantInfo> applicants;

    @Getter
    @NoArgsConstructor
    public static class ApplicantInfo {
        private Long applicantId;
        private String name;
        private LocalDate applyDate;
        private String status;

        @Builder
        public ApplicantInfo(Long applicantId, String name, LocalDate applyDate, String status) {
            this.applicantId = applicantId;
            this.name = name;
            this.applyDate = applyDate;
            this.status = status;
        }
    }

    @Builder
    public ShowProjectApplicantsListResponseVo(int totalElements, int totalPages, int currentPage,
        List<ApplicantInfo> applicants) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.applicants = applicants;
    }

    public static ShowProjectApplicantsListResponseVo dtoToVo(
        ProjectApplicantsResponseDto projectApplicantsResponseDto) {

        List<ApplicantInfo> applicantInfoList = projectApplicantsResponseDto.getApplicants().stream()
            .map(dtoApplicant -> ApplicantInfo.builder()
                .applicantId(dtoApplicant.getApplicantId())
                .name(dtoApplicant.getName())
                .applyDate(dtoApplicant.getApplyDate())
                .status(dtoApplicant.getStatus())
                .build())
            .collect(Collectors.toList());

        return ShowProjectApplicantsListResponseVo.builder()
            .totalElements(projectApplicantsResponseDto.getTotalElements())
            .totalPages(projectApplicantsResponseDto.getTotalPages())
            .currentPage(projectApplicantsResponseDto.getCurrentPage())
            .applicants(applicantInfoList)
            .build();
    }
}
