package com.archisemtle.semtlewebserverspring.dto.member;

import lombok.Data;

@Data
public class ExcelAddMemberRequestDto {
    private String email;
    private String studentId;
    private String username;
    private String phone;
    private String role;
}