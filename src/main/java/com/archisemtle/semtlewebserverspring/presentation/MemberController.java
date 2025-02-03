package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.MemberService;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtToken;
import com.archisemtle.semtlewebserverspring.dto.MemberFirstSaveDto;
import com.archisemtle.semtlewebserverspring.dto.MemberRegistrationDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 입력
    @PostMapping("/members")
    public ResponseEntity<Member> save(@RequestBody MemberRegistrationDto memberRegistrationDto) {
        Member member = memberService.save(memberRegistrationDto);
        return ResponseEntity.ok(member);
    }

    // 회원 정보 초기 입력
    @PostMapping("/members/First")
    public ResponseEntity<Member> firstSave(@RequestBody MemberFirstSaveDto memberFirstSaveDto) {
        Member member = memberService.firstSave(memberFirstSaveDto);
        return ResponseEntity.ok(member);
    }
    // 로그인
    @PostMapping("/members/login")
    public JwtToken Login(@RequestBody MemberRegistrationDto LoginDto) {
        String studentId = LoginDto.getStudentId();
        String password = LoginDto.getPassword();
        JwtToken jwtToken = memberService.Login(studentId, password);
        log.info("request studentId = {}, password = {}", studentId, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }
}