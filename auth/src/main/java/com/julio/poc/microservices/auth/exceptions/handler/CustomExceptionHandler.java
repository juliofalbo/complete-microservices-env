package com.julio.poc.microservices.auth.exceptions.handler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errors = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce((error, error2) -> error + ", " + error2).orElse("");
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), "Validation error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIExceptionResponse);
    }
}
