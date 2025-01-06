package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.MemberService;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 개인 정보 조회
    // 입력 - member_id
    // 출력 - 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @GetMapping("/api/v1/members/{member_id}")
    public ResponseEntity<Member> showMember(@PathVariable int member_id){
        Member member = memberService.findById(member_id);

        if(member == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member);
    }

    // 개인 정보 수정
    // uuid, 이름, 생년월일, 전화번호, 학번, 프로필 사진
    @PatchMapping("/api/v1/members/{member_id}")
    public ResponseEntity<Member> updateMember(@PathVariable int member_id, @RequestBody MemberRequestDto memberRequestDto) {
        try {
            memberService.update(member_id, memberRequestDto);
            return ResponseEntity.ok().build();
        }catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
