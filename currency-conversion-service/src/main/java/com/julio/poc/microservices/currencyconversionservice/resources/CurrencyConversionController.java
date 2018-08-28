package com.julio.poc.microservices.currencyconversionservice.resources;

import com.julio.poc.microservices.currencyconversionservice.dtos.CurrencyConversion;
import com.julio.poc.microservices.currencyconversionservice.service.CurrencyConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @GetMapping("from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
        log.info("M=getCurrencyConversion, PS: from: {}, to: {}, quantity: {}", from, to, quantity);
        return currencyConversionService.getExchangeValue(from, to, quantity);
    }

}
