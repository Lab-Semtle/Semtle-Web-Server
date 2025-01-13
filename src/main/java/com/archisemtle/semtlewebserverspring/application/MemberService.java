package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.ShowMember;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import java.util.UUID;

public interface MemberService {
    void save(MemberRequestDto memberRequestDto);
    ShowMember show(UUID uuid);
    void update(UUID uuid , UpdateMemberRequestDto updateMemberRequestDto);
}
