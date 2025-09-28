package com.finalproject.sos.config;


import com.finalproject.sos.domain.common.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    //filterChain Bean 등록
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //모든 URL 허용
                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/member/auth/").permitAll()
//                                .requestMatchers("/seller/**").hasRole("SELLER")
//                                .requestMatchers("member/**").hasRole("MEMBER")
//                                .requestMatchers("admin/auth/").hasRole("ADMIN")
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                        )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //passwordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
