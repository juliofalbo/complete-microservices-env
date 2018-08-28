package com.julio.poc.microservices.limitsservice.resources;

import com.julio.poc.microservices.limitsservice.Configuration;
import com.julio.poc.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration getLimitsFromConfigurations() {
        return new LimitConfiguration(configuration.getMin(), configuration.getMax());
    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallbackConfiguration")
    public LimitConfiguration faultToleranceExample() {
        throw new RuntimeException("Not available");
    }

    public LimitConfiguration fallbackConfiguration() {
        return new LimitConfiguration(7, 1000);
    }
}
