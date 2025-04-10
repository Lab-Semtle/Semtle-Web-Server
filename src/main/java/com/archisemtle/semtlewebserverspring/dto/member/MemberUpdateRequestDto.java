package com.archisemtle.semtlewebserverspring.dto.member;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
public class MemberUpdateRequestDto {

    private String studentId;
    private String username;
    private LocalDate birth;
    private String phone;
    //private String imageUrl;

}
