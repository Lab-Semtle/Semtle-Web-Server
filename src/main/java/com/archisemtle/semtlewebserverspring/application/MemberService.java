package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.LoginResponseDto;
import com.archisemtle.semtlewebserverspring.dto.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ShowMemberResponseDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import java.util.UUID;

public interface MemberService {
    Member save(MemberRegistrationRequestDto memberRegistrationRequestDto);
    ShowMemberResponseDto show(UUID uuid);
    void update(UUID uuid , UpdateMemberRequestDto updateMemberRequestDto);
    LoginResponseDto Login(LoginRequestDto loginRequestDto);
}
