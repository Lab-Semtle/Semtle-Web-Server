package com.archisemtle.semtlewebserverspring.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberPasswordResetEmailResponseDto {
    private String resetLinkExpiration;
}
