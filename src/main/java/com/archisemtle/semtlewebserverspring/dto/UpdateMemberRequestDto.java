package com.archisemtle.semtlewebserverspring.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateMemberRequestDto {

    private String name;
    private Date birth;
    private String phone;
    //private String imageUrl;

}
