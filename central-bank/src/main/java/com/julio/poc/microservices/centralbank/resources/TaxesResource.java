package com.julio.poc.microservices.centralbank.resources;

import com.julio.poc.microservices.centralbank.annotations.PopulatePortOnObjectResponse;
import com.julio.poc.microservices.centralbank.dto.Taxes;
import com.julio.poc.microservices.centralbank.services.TaxesService;
import com.julio.poc.microservices.centralbank.specifications.builder.JApiSpecificationBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/taxes")
public class TaxesResource {

    @Autowired
    private TaxesService service;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping
    public Taxes searchTaxes(@RequestParam("operation") String operation) {
        log.info("Method:searchTaxes Params: {}", operation);
        return service.searchTaxes(operation);
    }

    public Taxes fallback(String operation) {
        log.info("ERROR [fallback in action] Method:searchTaxes Param: {}", operation);
        return service.fallback(operation);
    }

}
