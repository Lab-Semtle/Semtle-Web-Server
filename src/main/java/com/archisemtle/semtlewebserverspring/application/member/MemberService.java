package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.ExcelAddMemberResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberDeactiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberUpdateRequestDto;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Member save(MemberRegistrationRequestDto memberRegistrationRequestDto);
    ExcelAddMemberResponseDto excelAddMember(MultipartFile file) throws IOException;
    MemberReadResponseDto show(UUID uuid);
    void update(UUID uuid , MemberUpdateRequestDto memberUpdateRequestDto);
    public void deactivateMember(UUID uuid, MemberDeactiveRequestDto memberDeactiveRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
