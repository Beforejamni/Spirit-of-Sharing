package com.finalproject.sos.domain.common.custom;

import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.auth.repository.SignInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SignInRepository signInRepository;


    //사용자 정보를 불러오기
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SignIn signIn = signInRepository.findWithMemberByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 없습니다."));
        return new CustomUserDetails(signIn);
    }
}
