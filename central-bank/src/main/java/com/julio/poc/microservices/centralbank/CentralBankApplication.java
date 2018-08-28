package com.julio.poc.microservices.centralbank;

import brave.sampler.Sampler;
import com.julio.poc.microservices.centralbank.entities.Operation;
import com.julio.poc.microservices.centralbank.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients("com.julio.poc.microservices.centralbank.clients")
@EnableDiscoveryClient
@EnableHystrix
public class CentralBankApplication implements CommandLineRunner {

    @Autowired
    private OperationService operationTypeService;

    public static void main(String[] args) {
        SpringApplication.run(CentralBankApplication.class, args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @Override
    public void run(String... args) throws Exception {
        operationTypeService.save(new Operation("loan", new BigDecimal(1.8)));
        operationTypeService.save(new Operation("financing", new BigDecimal(0.5)));
    }
}
