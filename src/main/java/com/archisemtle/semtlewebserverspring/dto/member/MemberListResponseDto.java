package com.archisemtle.semtlewebserverspring.dto.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;


@Data
@Builder
public class MemberListResponseDto {
    private int totalMembers;
    private int currentPage;
    private int totalPages;
    private List<UUID> members;

    public static MemberListResponseDto from(Page<Member> memberPage) {
        return MemberListResponseDto.builder()
            .totalMembers((int) memberPage.getTotalElements())
            .currentPage(memberPage.getTotalPages() > 0 ? memberPage.getNumber() + 1 : 1)
            .totalPages(memberPage.getTotalPages())
            .members(memberPage.map(Member::getUuid).toList())
            .build();
    }
}
