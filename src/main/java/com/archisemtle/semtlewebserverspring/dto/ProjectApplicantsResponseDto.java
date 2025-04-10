package com.archisemtle.semtlewebserverspring.dto;

import com.archisemtle.semtlewebserverspring.domain.Apply;
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
public class ProjectApplicantsResponseDto {

    private int totalElements;
    private int totalPages;
    private int currentPage;
    private List<ApplyInfo> applys;

    @Getter
    @NoArgsConstructor
    public static class ApplyInfo {
        private Long applyId;
        private String username;
        private LocalDateTime applyDate;
        private String status;

        @Builder
        public ApplyInfo(Long applyId, String username, LocalDateTime applyDate, String status) {
            this.applyId = applyId;
            this.username = username;
            this.applyDate = applyDate;
            this.status = status;
        }
    }

    public static ProjectApplicantsResponseDto entityToDto(Page<Apply> applicantsPage, int currentPage) {
        List<Apply> applys = applicantsPage.getContent();
        int totalElements = (int) applicantsPage.getTotalElements();

        List<ApplyInfo> applyInfoList = applys.stream()
            .map(apply -> ApplyInfo.builder()
                .applyId(apply.getApplyId())
                .username(apply.getMember().getUsername())
                .applyDate(apply.getApplyDate())
                .status(apply.getStatus())
                .build())
            .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / applys.size());

        return ProjectApplicantsResponseDto.builder()
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .applys(applyInfoList)
            .build();
    }

}
