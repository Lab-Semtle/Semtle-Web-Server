package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtToken;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtTokenProvider;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ShowMember;
import com.archisemtle.semtlewebserverspring.dto.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.LoginResponseDto;
import com.archisemtle.semtlewebserverspring.dto.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ShowMemberResponseDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateMemberRequestDto;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Member save(MemberRegistrationRequestDto memberRegistrationRequestDto) {
        return memberRepository.save(memberRegistrationRequestDto.toEntity(passwordEncoder));
    }

    // Member 조회 메서드
    @Override
    public ShowMemberResponseDto show(UUID uuid) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));
        ShowMember showMember = ShowMember.builder()
            .name(member.getUsername())
            .birth(member.getBirth())
            .phone(member.getPhone())
            .build();
        ShowMemberResponseDto showMemberDto = ShowMemberResponseDto.entityToDto(showMember);
        return showMemberDto;
    }

    // Member 수정 메서드
    @Override
    @Transactional
    public void update(UUID uuid , UpdateMemberRequestDto updateMemberRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));

        Member updatedMember = Member.builder()
            .memberId(member.getMemberId())
            .uuid(member.getUuid())
            .email(member.getEmail())
            .password(member.getPassword())
            .username(updateMemberRequestDto.getName() != null ? updateMemberRequestDto.getName() : member.getUsername()) // 업데이트 값 적용
            .birth(updateMemberRequestDto.getBirth() != null ? updateMemberRequestDto.getBirth() : member.getBirth())
            .phone(updateMemberRequestDto.getPhone() != null ? updateMemberRequestDto.getPhone() : member.getPhone())
            .studentId(member.getStudentId())
            .manageApprovalStatus(member.isManageApprovalStatus())
            .roles(member.getRoles())
            .build();

        memberRepository.save(updatedMember);
    }

    @Override
    public LoginResponseDto Login(LoginRequestDto loginRequestDto) {
        try {
            Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

            return LoginResponseDto.builder()
                .uuid(member.getUuid())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .username(member.getUsername())
                .roles(member.getRoles())
                .manageApprovalStatus(member.isManageApprovalStatus())
                .build();

        } catch (Exception e) {
            log.error("인증 실패 : {}", e.getMessage());
            throw new RuntimeException("정확한 이메일과 비밀번호를 입력해주세요.");
        }
    }
}
