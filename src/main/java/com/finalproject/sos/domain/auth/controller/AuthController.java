package com.finalproject.sos.domain.auth.controller;


import com.finalproject.sos.domain.auth.dto.request.SignInRequestDto;
import com.finalproject.sos.domain.auth.dto.request.UpdatePasswordRequest;
import com.finalproject.sos.domain.auth.service.AuthService;
import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody SignInRequestDto signInRequestDto){


        return new ResponseEntity<>(authService.signIn(signInRequestDto), HttpStatus.OK);
    }

    @PostMapping("/update/password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        authService.updatePassword(userDetails, updatePasswordRequest);

        return ResponseEntity.ok().body("비밀번호 변경이 완료되었습니다.");
    }
}
