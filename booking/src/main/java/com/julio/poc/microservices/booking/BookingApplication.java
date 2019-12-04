package com.julio.poc.microservices.booking;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.alturkovic.lock.redis.configuration.EnableRedisDistributedLock;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@EnableRedisDistributedLock
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

}
