package com.finalproject.sos.domain.auth.service;

import com.finalproject.sos.domain.auth.dto.request.SignInRequestDto;
import com.finalproject.sos.domain.auth.dto.request.SignUpRequestDto;
import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.auth.repository.SignInRepository;
import com.finalproject.sos.domain.common.util.JwtUtil;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.entity.RoleType;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SignInRepository signInRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Map<String,String> singUpMember(SignUpRequestDto signUpRequestDto) {

        //아이디 중복 조회
        if (signInRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 아이디입니다.");
        }

        //password 암호화 및 DB 저장
        SignIn signIn = signUpRequestDto.toSignIn(passwordEncoder.encode(signUpRequestDto.getPassword()));
        signInRepository.save(signIn);

        //일반 사용자 권한 부여 및 DB signIn이랑 연결
        Member member = signUpRequestDto.toMember(RoleType.COSTUMER);
        member.linkSignIn(signIn);

        //DB 저장
        Member save = memberRepository.save(member);

        //Token 발급
        String accessToken = jwtUtil.createAccessToken(save.getMemberId(), save.getSignIn().getUsername(), save.getRoleType());
        String refreshToken = jwtUtil.createRefreshToken(save.getMemberId());

        //출력 저장
        //순서 보장
        Map<String, String> tokenMap = new LinkedHashMap<>();

        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }

    public Map<String, String> signIn(SignInRequestDto signInRequestDto) {

        SignIn signIn = signInRepository.findWithMemberByUsername(signInRequestDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));


        if (!passwordEncoder.matches(signInRequestDto.getPassword(), signIn.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(signIn.getMember().getMemberId(),
                                                        signIn.getUsername(),
                                                        signIn.getMember().getRoleType());

        String refreshToken = jwtUtil.createRefreshToken(signIn.getMember().getMemberId());

        Map<String, String> tokenMap = new LinkedHashMap<>();

        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        tokenMap.put("roleType", signIn.getMember().getRoleType().name() );

        return tokenMap;
    }
}
