package com.julio.poc.microservices.currencyexchangeservice.resources;

import com.julio.poc.microservices.currencyexchangeservice.entities.ExchangeValue;
import com.julio.poc.microservices.currencyexchangeservice.services.CurrencyExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService service;

    @GetMapping
    public List<ExchangeValue> findAll() {
        return service.findAll();
    }

    @GetMapping("from/{from}/to/{to}")
    public ExchangeValue getExchangeValue(@PathVariable String from, @PathVariable String to) {
        log.info("M=getExchangeValue, PS: from: {}, to: {}", from, to);
        return service.findByFromAndTo(from, to);
    }

}
