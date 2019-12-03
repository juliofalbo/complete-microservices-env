package com.julio.poc.microservices.frontend.config;

import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.Module;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfiguration {

    @Bean
    public Module pageJacksonModule() {
        return new PageJacksonModule();
    }

    @Bean
    @Primary
    public Retryer retryer() {
        return new FeignRetryer(2000, 5000, 5);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
