package com.archisemtle.semtlewebserverspring.application.member;

import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Set<SimpleGrantedAuthority> authorities = member.getRole().equals("ADMIN") ?
            Set.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")) :
            Set.of(new SimpleGrantedAuthority("USER"));

        return member;
    }

    public UserDetails loadUserByUuid(UUID uuid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUuid(uuid)
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Set<SimpleGrantedAuthority> authorities = member.getRole().equals("ADMIN") ?
            Set.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")) :
            Set.of(new SimpleGrantedAuthority("USER"));

        return member;
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
            .username(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRole())
            .build();
    }
}
