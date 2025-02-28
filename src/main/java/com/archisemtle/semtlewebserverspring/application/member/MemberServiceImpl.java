package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtToken;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtTokenProvider;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.dto.member.ExcelAddMemberResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginRequestDto;
import com.archisemtle.semtlewebserverspring.dto.member.LoginResponseDto;
import com.archisemtle.semtlewebserverspring.dto.member.MemberDeactiveRequestDto;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


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

    @Override
    @Transactional
    public ExcelAddMemberResponseDto excelAddMember(MultipartFile file) throws IOException {

        List<Member> membersToSave = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        int successCount = 0;
        int failedCount = 0;

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String email = getCellValue(row.getCell(0));
                String studentId = getCellValue(row.getCell(1));
                String username = getCellValue(row.getCell(2));
                String phone = getCellValue(row.getCell(3));
                String role = getCellValue(row.getCell(4));

                if (email.isBlank() || !email.contains("@")) {
                    errorMessages.add("이메일 형식이 올바르지 않음: " + email);
                    failedCount++;
                    continue;
                }
                if (memberRepository.findByEmail(email).isPresent()) {
                    errorMessages.add("이메일 중복: " + email);
                    failedCount++;
                    continue;
                }

                membersToSave.add(Member.builder()
                    .uuid(UUID.randomUUID())
                    .email(email)
                    .password(passwordEncoder.encode("#semtle308"))
                    .studentId(studentId)
                    .username(username)
                    .birth(parseDate("2025-01-01"))
                    .phone(phone)
                    .role(role)
                    .manageApprovalStatus(false)
                    .build()
                );
                successCount++;
            }
        }

        memberRepository.saveAll(membersToSave);

        return ExcelAddMemberResponseDto.builder()
            .successCount(successCount)
            .failedCount(failedCount)
            .errors(errorMessages)
            .build();
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            return new Date();
        }
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
