package com.archisemtle.semtlewebserverspring.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPasswordResetEmailRequestDto {
    private String email;
}
