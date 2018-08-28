package com.julio.poc.microservices.currencyexchangeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyExchangeFoundException extends RuntimeException {

    public CurrencyExchangeFoundException(String message) {
        super(message);
    }

}
