package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtToken;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtTokenProvider;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberDeactiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberReadResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberRegistrationRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberUpdateRequestDto;
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

    @Override
    @Transactional
    public Member save(MemberRegistrationRequestDto memberRegistrationRequestDto) {
        return memberRepository.save(memberRegistrationRequestDto.toEntity(passwordEncoder));
    }

    // Member 조회 메서드
    @Override
    public MemberReadResponseDto show(UUID uuid) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));

        return MemberReadResponseDto.builder()
            .uuid(member.getUuid())
            .email(member.getEmail())
            .password(member.getPassword())
            .studentId(member.getStudentId())
            .username(member.getUsername())
            .birth(member.getBirth())
            .phone(member.getPhone())
            .role(member.getRole())
            .manageApprovalStatus(member.isManageApprovalStatus())
            .build();
    }

    // Member 수정 메서드
    @Override
    @Transactional
    public void update(UUID uuid , MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = memberRepository.findByUuid(uuid).orElseThrow(()-> new BaseException(
            BaseResponseStatus.NO_EXIST_MEMBERS));

        Member updatedMember = Member.builder()
            .memberId(member.getMemberId())
            .uuid(member.getUuid())
            .email(member.getEmail())
            .password(member.getPassword())
            .studentId(member.getStudentId() != null ? memberUpdateRequestDto.getStudentId() : member.getStudentId())
            .username(memberUpdateRequestDto.getUsername() != null ? memberUpdateRequestDto.getUsername() : member.getUsername()) // 업데이트 값 적용
            .birth(memberUpdateRequestDto.getBirth() != null ? memberUpdateRequestDto.getBirth() : member.getBirth())
            .phone(memberUpdateRequestDto.getPhone() != null ? memberUpdateRequestDto.getPhone() : member.getPhone())
            .role(member.getRole())
            .manageApprovalStatus(member.isManageApprovalStatus())
            .build();

        memberRepository.save(updatedMember);
    }

    @Override
    @Transactional
    public void deactivateMember(UUID uuid, MemberDeactiveRequestDto memberDeactiveRequestDto) {
        Member member = memberRepository.findByUuid(uuid)
            .orElseThrow(() -> new RuntimeException("해당 UUID의 회원을 찾을 수 없습니다."));

        member.setManageApprovalStatus(memberDeactiveRequestDto.isManageApprovalStatus());

        memberRepository.save(member);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

            if (!member.isManageApprovalStatus()) {
                throw new BaseException(BaseResponseStatus.NOT_APPROVAL_MEMBER);
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, member.getUuid());

            return LoginResponseDto.builder()
                .uuid(member.getUuid())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .username(member.getUsername())
                .build();

        } catch (Exception e) {
            log.error("인증 실패 : {}", e.getMessage());
            throw new RuntimeException("정확한 이메일과 비밀번호를 입력해주세요.");
        }
    }
}
