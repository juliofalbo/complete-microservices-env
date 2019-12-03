package com.julio.poc.microservices.frontend.config;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    public Exception decode(String methodKey, Response response) {
        if (response.status() > 499 && response.status() < 600) {
            throw new RetryableException(
                response.status(), 
                "Service Unavailable", 
                response.request().httpMethod(), 
                null);
        } else {
            return new RuntimeException("error");
        }
    }
}