package com.archisemtle.semtlewebserverspring.vo;

import com.archisemtle.semtlewebserverspring.dto.ProjectApplicantsResponseDto;
import java.time.LocalDateTime;
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

    @Builder
    public ShowProjectApplicantsListResponseVo(int totalElements, int totalPages, int currentPage,
        List<ApplyInfo> applys) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.applys = applys;
    }

    public static ShowProjectApplicantsListResponseVo dtoToVo(
        ProjectApplicantsResponseDto projectApplicantsResponseDto) {

        List<ApplyInfo> applyInfoList = projectApplicantsResponseDto.getApplys().stream()
            .map(apply -> ApplyInfo.builder()
                .applyId(apply.getApplyId())
                .username(apply.getUsername())
                .applyDate(apply.getApplyDate())
                .status(apply.getStatus())
                .build())
            .collect(Collectors.toList());

        return ShowProjectApplicantsListResponseVo.builder()
            .totalElements(projectApplicantsResponseDto.getTotalElements())
            .totalPages(projectApplicantsResponseDto.getTotalPages())
            .currentPage(projectApplicantsResponseDto.getCurrentPage())
            .applys(applyInfoList)
            .build();
    }
}
