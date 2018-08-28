package com.julio.poc.microservices.centralbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OperationTypeNotFoundException extends RuntimeException {

    public OperationTypeNotFoundException(String message) {
        super(message);
    }

}
