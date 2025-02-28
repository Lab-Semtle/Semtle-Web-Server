package com.archisemtle.semtlewebserverspring.vo.member;

import com.archisemtle.semtlewebserverspring.dto.member.MemberListResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberListResponseVo {
    private int totalMembers;
    private int currentPage;
    private int totalPages;
    private List<UUID> members;

    public static MemberListResponseVo dtoToVo(MemberListResponseDto memberListResponseDto) {
        return MemberListResponseVo.builder()
            .totalMembers(memberListResponseDto.getTotalMembers())
            .currentPage(memberListResponseDto.getCurrentPage())
            .totalPages(memberListResponseDto.getTotalPages())
            .members(memberListResponseDto.getMembers())
            .build();
    }
}
