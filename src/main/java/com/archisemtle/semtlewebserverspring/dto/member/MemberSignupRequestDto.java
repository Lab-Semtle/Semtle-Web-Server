package com.archisemtle.semtlewebserverspring.dto.member;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberSignupRequestDto {

    private String email;
    private String password;
    private String name;
    private Date birth;
    private String studentId;

}
