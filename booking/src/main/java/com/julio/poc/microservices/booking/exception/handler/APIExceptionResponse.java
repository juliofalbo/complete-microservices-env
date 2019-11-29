package com.julio.poc.microservices.booking.exception.handler;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

}
