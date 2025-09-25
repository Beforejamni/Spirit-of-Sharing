package com.finalproject.sos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.local")
public class KakaoLocalProps {

    private String baseUrl;

    private String restApiKey;

    public String getBaseUrl(){ return baseUrl; }

    public String getRestApiKey() { return  restApiKey; }

    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl;}

    public void setRestApiKey(String restApiKey) {this.restApiKey = restApiKey;}
}
