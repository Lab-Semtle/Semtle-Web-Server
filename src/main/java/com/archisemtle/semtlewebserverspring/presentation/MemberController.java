package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.MemberService;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원 입력
    @PostMapping("/api/v1/members")
    public Member save(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.save(memberRequestDto);
        return null;
    }
}