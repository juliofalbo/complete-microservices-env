package com.julio.poc.microservices.netflixzuulapigatewayserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizeException extends RuntimeException {

    public UnauthorizeException(String message) {
        super(message);
    }

}
