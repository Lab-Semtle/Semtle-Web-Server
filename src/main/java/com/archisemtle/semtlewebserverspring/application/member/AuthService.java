package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.dto.member.MemberSignupRequestDto;

public interface AuthService {

    void memberSignup(MemberSignupRequestDto memberSignupRequestDto);
}
