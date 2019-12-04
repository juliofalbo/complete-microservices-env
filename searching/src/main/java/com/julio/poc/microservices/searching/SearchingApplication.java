package com.julio.poc.microservices.searching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class SearchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchingApplication.class, args);
    }

}
