package com.archisemtle.semtlewebserverspring.domain;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    private String email;

    private String password;

    private String name;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String phone;

    private String studentId;

    private String role;

    private boolean manageApprovalStatus;

    @Builder
    public Member(String email, String password, String name, Date birth, String phone,
        String studentId, boolean manageApprovalStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.studentId = studentId;
        this.manageApprovalStatus = manageApprovalStatus;
    }
}
