package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Applicant;
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
public class ProjectApplicantsResponseDto {

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

    public static ProjectApplicantsResponseDto entityToDto(Page<Applicant> applicantsPage, int currentPage) {
        List<Applicant> applicants = applicantsPage.getContent();
        int totalElements = (int) applicantsPage.getTotalElements();

        List<ApplicantInfo> applicantInfoList = applicants.stream()
            .map(applicant -> ApplicantInfo.builder()
                .applicantId(applicant.getApplicantId())
                .name(applicant.getName())
                .applyDate(applicant.getApplyDate())
                .status(applicant.getStatus())
                .build())
            .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / applicants.size());

        return ProjectApplicantsResponseDto.builder()
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .applicants(applicantInfoList)
            .build();
    }

}
