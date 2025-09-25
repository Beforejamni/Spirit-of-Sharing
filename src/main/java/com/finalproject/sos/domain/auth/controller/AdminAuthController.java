package com.finalproject.sos.domain.auth.controller;


import com.finalproject.sos.domain.auth.dto.request.SignUpRequestDto;
import com.finalproject.sos.domain.auth.service.AuthService;
import com.finalproject.sos.domain.member.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminAuthController {


    private final AuthService authService;

    @PostMapping("/auth/signup/seller")
    public ResponseEntity<Map<String, String>> signUpSeller(@Validated @RequestBody SignUpRequestDto signUpRequestDto) {

        return new ResponseEntity<>(authService.singUp(signUpRequestDto, RoleType.SELLER),HttpStatus.CREATED);
    }
}
