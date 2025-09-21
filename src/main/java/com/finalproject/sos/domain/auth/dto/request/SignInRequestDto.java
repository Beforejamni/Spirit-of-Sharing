package com.finalproject.sos.domain.auth.dto.request;


import lombok.Getter;

@Getter
public class SignInRequestDto {

    private String username;

    private String password;
}
