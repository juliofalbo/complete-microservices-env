package com.julio.poc.microservices.netflixzuulapigatewayserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RateLimitException extends RuntimeException {

    public RateLimitException(String message) {
        super(message);
    }

}
