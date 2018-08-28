package com.julio.poc.microservices.currencyconversionservice.service;

import com.julio.poc.microservices.currencyconversionservice.annotations.PopulatePortOnObjectResponse;
import com.julio.poc.microservices.currencyconversionservice.clients.CurrencyExchangeClient;
import com.julio.poc.microservices.currencyconversionservice.dtos.CurrencyConversion;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConversionService {

    @Autowired
    private CurrencyExchangeClient currencyExchangeClient;

    @PopulatePortOnObjectResponse(fieldName = "currencyConversionMicroservicePort")
    public CurrencyConversion getExchangeValue(@NonNull final String from,
                                               @NonNull final String to, @NonNull final BigDecimal quantity){
        CurrencyConversion exchangeValue = currencyExchangeClient.getExchangeValue(from, to);
        exchangeValue.setQuantity(quantity);
        exchangeValue.setTotalCalculatedAmount(quantity.multiply(exchangeValue.getConversionMultiple()));
        return exchangeValue;
    }

}
