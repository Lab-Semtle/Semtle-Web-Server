//package com.archisemtle.semtlewebserverspring.dto;
//
//import com.archisemtle.semtlewebserverspring.domain.Member;
//import java.util.Date;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class ShowMemberRequestDto {
//    private String email;
//    private String password;
//    private String name;
//    private Date birth;
//    private String phone;
//    private String studentId;
//    //private String imageUrl;
//
//    @Builder
//    public ShowMemberRequestDto(String email, String password, String name, Date birth, String phone,
//        String studentId) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.birth = birth;
//        this.phone = phone;
//        this.studentId = studentId;
//    }
//
//    public Member toEntity() {
//        return Member.builder()
//            .email(email)
//            .password(password)
//            .name(name)
//            .birth(birth)
//            .phone(phone)
//            .studentId(studentId)
//            .build();
//    }
//}
