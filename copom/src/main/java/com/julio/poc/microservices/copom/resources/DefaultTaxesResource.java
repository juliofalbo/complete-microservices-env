package com.julio.poc.microservices.copom.resources;

import com.julio.poc.microservices.copom.dto.DefaultTaxes;
import com.julio.poc.microservices.copom.service.DefaultTaxesService;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class DefaultTaxesResource {

    @Autowired
    private DefaultTaxesService defaultTaxesService;

    @GetMapping("/default-taxes")
    public DefaultTaxes getDefaultTaxes() {
        return defaultTaxesService.getDefaultTaxes();
    }

    @GetMapping("/default-taxes-refresh")
    @HystrixCommand(fallbackMethod = "fallbackConfiguration")
    public DefaultTaxes faultToleranceExample() {
        throw new RuntimeException("Not available");
    }

    public DefaultTaxes fallbackConfiguration() {
        return new DefaultTaxes(new BigDecimal(0.1));
    }
}
