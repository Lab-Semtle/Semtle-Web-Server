package com.archisemtle.semtlewebserverspring.dto.member;

import java.util.Date;
import lombok.Data;

@Data
public class MemberUpdateRequestDto {

    private String studentId;
    private String username;
    private Date birth;
    private String phone;
    //private String imageUrl;

}
