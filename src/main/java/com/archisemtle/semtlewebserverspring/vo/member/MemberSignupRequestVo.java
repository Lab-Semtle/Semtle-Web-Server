package com.archisemtle.semtlewebserverspring.vo.member;

import com.archisemtle.semtlewebserverspring.dto.member.MemberSignupRequestDto;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class MemberSignupRequestVo {

    private String email;
    private String password;
    private String name;
    private Date birth;
    private String studentId;

    public static MemberSignupRequestDto voToDto(MemberSignupRequestVo memberSignupRequestVo) {
        return MemberSignupRequestDto.builder()
            .birth(memberSignupRequestVo.getBirth())
            .email(memberSignupRequestVo.getEmail())
            .studentId(memberSignupRequestVo.getStudentId())
            .name(memberSignupRequestVo.getName())
            .password(memberSignupRequestVo.getPassword())
            .build();
    }
}
