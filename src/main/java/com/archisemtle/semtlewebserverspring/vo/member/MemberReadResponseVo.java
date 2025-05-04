package com.archisemtle.semtlewebserverspring.vo.member;

import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberReadResponseVo {
    private UUID uuid;
    private String email;
    private String password;
    private String studentId;
    private String username;
    private Date birth;
    private String phone;
    private String role;
    private boolean manageApprovalStatus;

    public static MemberReadResponseVo dtoToVo(
        MemberReadResponseDto memberReadResponseDto) {
        return MemberReadResponseVo.builder()
            .uuid(memberReadResponseDto.getUuid())
            .email(memberReadResponseDto.getEmail())
            .password(memberReadResponseDto.getPassword())
            .studentId(memberReadResponseDto.getStudentId())
            .username(memberReadResponseDto.getUsername())
            .birth(memberReadResponseDto.getBirth())
            .phone(memberReadResponseDto.getPhone())
            .role(memberReadResponseDto.getRole())
            .manageApprovalStatus(memberReadResponseDto.isManageApprovalStatus())
            .build();
    }
}
