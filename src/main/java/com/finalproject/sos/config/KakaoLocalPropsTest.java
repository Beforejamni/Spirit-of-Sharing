package com.finalproject.sos.config;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoLocalPropsTest {

    private final KakaoLocalProps kakaoLocalProps;

    @PostConstruct
    void init() {
        System.out.println("baseUrl= " + kakaoLocalProps.getBaseUrl());
        System.out.println("restApiKey= " + kakaoLocalProps.getRestApiKey());
    }
}
