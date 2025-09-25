package com.finalproject.sos.domain.common.custom;

import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.member.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final SignIn signIn;



    //권한 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleType roleType = signIn.getMember().getRoleType();
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleType));
    }

    //비밀번호 가져오기
    @Override
    public String getPassword() {
        return signIn.getPassword();
    }


    public Long getMemberId() {return  signIn.getMember().getMemberId();}

    //Username 가져오기
    @Override
    public String getUsername() {
        return signIn.getUsername();
    }
}
