package com.archisemtle.semtlewebserverspring.common.utils;

import com.archisemtle.semtlewebserverspring.config.jwt.JwtAuthenticationFilter;
import com.archisemtle.semtlewebserverspring.config.jwt.JwtTokenProvider;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.infrastructure.MemberRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserUtils {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    /**
     * 유저 이름을 가져오는 메소드
     * @Param  BearerToken (헤더에서 가져올 것)
     * @Return UserName     (유저 이름을 추출)
     * */
    public String getUserUuid(String authorizationHeader){
        String token = extractToken(authorizationHeader);
        String userName = jwtTokenProvider.getAuthentication(token).getName();
        Optional<Member> member = memberRepository.findByEmail(userName);
        return member.get().getUuid().toString();
    }



    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("유효한 Bearer 토큰이 아닙니다.");
    }
}
