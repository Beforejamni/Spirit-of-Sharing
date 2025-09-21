package com.finalproject.sos.domain.common.filter;

import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.common.custom.CustomUserDetailsService;
import com.finalproject.sos.domain.common.util.JwtUtil;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    //필터 적용 X => 추후 수정
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if("/member/auth/signup".matches(request.getRequestURI()) ||
            "/auth/signin".matches(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "jwt 토큰이 필요합니다.");
            return;
        }

        try{

            String token = jwtUtil.subStringToken(header);

            Authentication authentication = jwtUtil.getAuthentication(token,customUserDetailsService);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (AuthException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());

        }

    }
}
