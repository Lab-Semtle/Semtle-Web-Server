package com.archisemtle.semtlewebserverspring.dto.member;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcelAddMemberResponseDto {
    private int successCount;
    private int failedCount;
    private List<String> errors;
}
