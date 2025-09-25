package com.finalproject.sos.config;


import feign.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@EnableConfigurationProperties(KakaoLocalProps.class)
public class KakaoFeignConfig {


    @Bean
    public RequestInterceptor kakaoAuth(KakaoLocalProps kakaoLocalProps) {

        return tmpl -> tmpl.header(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoLocalProps.getRestApiKey());
    }
}
