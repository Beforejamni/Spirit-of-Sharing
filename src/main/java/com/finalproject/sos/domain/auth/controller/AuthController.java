package com.finalproject.sos.domain.auth.controller;


import com.finalproject.sos.domain.auth.dto.request.SignInRequestDto;
import com.finalproject.sos.domain.auth.service.AuthService;
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
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody SignInRequestDto signInRequestDto){


        return new ResponseEntity<>(authService.signIn(signInRequestDto), HttpStatus.OK);
    }
}
