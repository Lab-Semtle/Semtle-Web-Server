//package com.archisemtle.semtlewebserverspring.domain;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import java.util.Date;
//import java.util.UUID;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity(name = "member")
//public class ShowMember {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int memberId;
//
//    @Column(nullable = false, unique = true)
//    private UUID uuid = UUID.randomUUID();
//
//    private String name;
//
//    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date birth;
//
//    private String phone;
//
//    //private String imageUrl;
//
//    @Builder
//    public ShowMember(String name, Date birth, String phone) {
//        this.name = name;
//        this.birth = birth;
//        this.phone = phone;
//    }
//}
