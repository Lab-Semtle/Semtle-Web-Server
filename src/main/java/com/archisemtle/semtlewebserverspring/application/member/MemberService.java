package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberUpdateRequestDto;
import java.util.UUID;

public interface MemberService {
    Member save(MemberRegistrationRequestDto memberRegistrationRequestDto);
    MemberReadResponseDto show(UUID uuid);
    void update(UUID uuid , MemberUpdateRequestDto memberUpdateRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
