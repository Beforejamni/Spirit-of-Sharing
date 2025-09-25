package com.finalproject.sos.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLongConfig {

    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;
    }
}
