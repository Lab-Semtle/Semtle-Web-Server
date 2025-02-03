package com.archisemtle.semtlewebserverspring.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    private String studentId;

    private String password;

    private String username;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String phone;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    private boolean manageApprovalStatus;

    @Override
    public String getUsername() {
        return studentId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public Member(UUID uuid, String studentId, String password, String username, Date birth, String phone, String email, List<String> roles, boolean manageApprovalStatus) {
        this.uuid = uuid;
        this.studentId = studentId;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
        this.manageApprovalStatus = manageApprovalStatus;
    }
}
