package com.finalproject.sos.domain.common.util;


import com.finalproject.sos.domain.common.custom.CustomUserDetailsService;
import com.finalproject.sos.domain.member.entity.RoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${JWT_SECRET}")
    private String secret;
    private Key key;
    private JwtParser parser;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final long ACCESS_TOKEN = 1000L * 60 * 60;
    private static final long REFRESH_TOKEN = 1000L * 60 * 60 * 24 * 7;
    private static final String BEARER_PREFIX = "Bearer ";


    @PostConstruct
    public void init() {

       byte[] keyArray = secret.getBytes(StandardCharsets.UTF_8);

       this.key = Keys.hmacShaKeyFor(keyArray);

       this.parser = Jwts.parserBuilder().setSigningKey(key).build();

    }

    //Access Token 생성
    public String createAccessToken(Long memberId, String username, RoleType roleType) {

        return Jwts.builder()
                .setSubject(username)
                .claim("memberId", memberId)
                .claim("role", roleType.name())
                .claim("type", "access")
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
            return parser
                    .parseClaimsJws(token)
                    .getBody();
        } catch (DecodingException e) {
            throw new AuthException("토큰 디코딩 실패(Base64URL 형식 오류/개행/따옴표 혼입)");
        } catch (ExpiredJwtException e) {
            throw new AuthException("만료된 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new AuthException("토큰 형식이 잘못되었습니다.");
        } catch (JwtException e) {
            throw new jakarta.security.auth.message.AuthException("유효하지 않은 토큰입니다.");
        }
    }

    //Security 인증 객체로 변경
    public Authentication getAuthentication(String token, CustomUserDetailsService customUserDetailsService) throws AuthException {

        Claims claims = getClaims(token);

        String type = claims.get("type", String.class);

        if(!"access".equals(type)) {
            throw new AuthException("액세스 토큰이 아닙니다.");
        }

        String username = claims.getSubject();

        System.out.println(username);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

}
