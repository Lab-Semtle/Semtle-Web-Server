package com.archisemtle.semtlewebserverspring.dto.member;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberReadResponseDto {
    private UUID uuid;
    private String email;
    private String password;
    private String studentId;
    private String username;
    private LocalDate birth;
    private String phone;
    private String profileImageUrl;
    private String role;
    private boolean manageApprovalStatus;
}
