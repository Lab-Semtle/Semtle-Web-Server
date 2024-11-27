package com.example.test.presentation;

import com.example.test.application.MemberService;
import com.example.test.domain.Member;
import com.example.test.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return memberService.save(memberRequestDto);
    }
}