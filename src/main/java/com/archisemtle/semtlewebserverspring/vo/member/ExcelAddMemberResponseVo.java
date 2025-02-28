package com.archisemtle.semtlewebserverspring.vo.member;

import com.archisemtle.semtlewebserverspring.dto.member.ExcelAddMemberResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExcelAddMemberResponseVo {
    private int successCount;
    private int failedCount;
    private List<String> errors;

    public static ExcelAddMemberResponseVo dtoToVo(ExcelAddMemberResponseDto dto) {
        return ExcelAddMemberResponseVo.builder()
            .successCount(dto.getSuccessCount())
            .failedCount(dto.getFailedCount())
            .errors(dto.getErrors())
            .build();
    }
}
