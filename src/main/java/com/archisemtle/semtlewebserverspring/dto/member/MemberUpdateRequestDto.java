package com.archisemtle.semtlewebserverspring.dto.member;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MemberUpdateRequestDto {

    private String username;
    private LocalDate birth;
    private String phone;
    private String imageUrl;

}
