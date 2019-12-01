package com.julio.poc.microservices.financial.exception;

public class RetryableException extends RuntimeException {
    private static final long serialVersionUID = 6769829250639411880L;

    public RetryableException() {
        super();
    }

    public RetryableException(String s) {
        super(s);
    }
}
