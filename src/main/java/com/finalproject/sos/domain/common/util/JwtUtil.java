package com.finalproject.sos.domain.common.util;


import com.finalproject.sos.domain.common.custom.CustomUserDetailsService;
import com.finalproject.sos.domain.member.entity.RoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${JWT_SECRET}")
    private String secretKey;
    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final long ACCESS_TOKEN = 1000L * 60 * 60;
    private static final long REFRESH_TOKEN = 1000L * 60 * 60 * 24 * 7;
    private static final String BEARER_PREFIX = "Bearer ";


    @PostConstruct
    public void init() {

        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    //Access Token 생성
    public String createAccessToken(Long memberId, String username, RoleType roleType) {

        return Jwts.builder()
                .setSubject(username)
                .claim("memberId", memberId)
                .claim("role", roleType.name())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    //RefreshToken 생성
    public String createRefreshToken(Long memberId) {

        return Jwts.builder()
                .setSubject("refresh")
                .claim("memberId", memberId)
                .claim("type", "refresh")
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    //Bearer 짤라내기
    public String subStringToken(String token) throws ServerException {
        if(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }

        throw new ServerException("유효하지 않은 토큰 입니다.");
    }


    //Claims 가져오기
    private Claims getClaims(String token) throws  AuthException {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e) {
            throw new AuthException("만료된 토큰입니다.");

        }catch (JwtException e) {
            throw new AuthException("유효하지 않은 토큰입니다.");
        }
    }

    //Security 인증 객체로 변경
    public Authentication getAuthentication(String token, CustomUserDetailsService customUserDetailsService) throws AuthException {

        Claims claims = getClaims(token);
        String username = claims.getSubject();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

}
