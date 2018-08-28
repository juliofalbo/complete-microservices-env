package com.julio.poc.microservices.arruzzobank;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients("com.julio.poc.microservices.arruzzobank.clients")
@EnableDiscoveryClient
public class ArruzzoBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArruzzoBankApplication.class, args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
