package com.julio.poc.microservices.booking.config;

import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.Module;

@Configuration
public class FeignDecodeConfiguration {

    @Bean
    public Module pageJacksonModule() {
        return new PageJacksonModule();
    }
}