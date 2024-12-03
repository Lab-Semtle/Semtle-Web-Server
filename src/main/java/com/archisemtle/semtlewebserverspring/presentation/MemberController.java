package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.MemberService;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.MemberFirstSaveDto;
import com.archisemtle.semtlewebserverspring.dto.MemberRegistrationDto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 입력
    @PostMapping("/api/v1/members")
    public ResponseEntity<Member> save(@RequestBody MemberRegistrationDto memberRegistrationDto) {
        Member member = memberService.save(memberRegistrationDto);
        return ResponseEntity.ok(member);
    }

    // 회원 정보 초기 입력
    @PostMapping("/api/v1/members/First")
    public ResponseEntity<Member> firstSave(@RequestBody MemberFirstSaveDto memberFirstSaveDto) {
        Member member = memberService.firstSave(memberFirstSaveDto);
        return ResponseEntity.ok(member);
    }
}