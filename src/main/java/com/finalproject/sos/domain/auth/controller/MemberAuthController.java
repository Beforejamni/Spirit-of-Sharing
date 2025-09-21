package com.finalproject.sos.domain.auth.controller;

import com.finalproject.sos.domain.auth.dto.request.SignUpRequestDto;
import com.finalproject.sos.domain.auth.service.AuthService;
import com.finalproject.sos.domain.member.entity.RoleType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/auth")
public class MemberAuthController {

        private final AuthService authService;


    //일반 유저 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUpMember (@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        return new ResponseEntity<>(authService.singUp(signUpRequestDto, RoleType.COSTUMER), HttpStatus.CREATED);
    }
}
