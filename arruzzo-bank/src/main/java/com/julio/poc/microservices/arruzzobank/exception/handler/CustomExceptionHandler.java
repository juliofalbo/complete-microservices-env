package com.julio.poc.microservices.arruzzobank.exception.handler;


import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<APIExceptionResponse> defaultExp(Exception ex, WebRequest request) {
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIExceptionResponse);
    }

    @ExceptionHandler(FeignException.class)
    private ResponseEntity<APIExceptionResponse> feignExp(FeignException ex, WebRequest request) {
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(ex.status()).body(APIExceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errors = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce((error, error2) -> error + ", " + error2).orElse("");
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), "Validation error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIExceptionResponse);
    }
}
