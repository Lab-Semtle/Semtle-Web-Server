package com.archisemtle.semtlewebserverspring.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDeactiveRequestDto {
    private boolean manageApprovalStatus;
    private String role;
    private String reason;
}
