package com.julio.poc.microservices.booking.exception;

public class ConflictElementException extends RuntimeException {
    private static final long serialVersionUID = 6769829250639411880L;

    public ConflictElementException() {
        super();
    }

    public ConflictElementException(String s) {
        super(s);
    }
}
