package com.julio.poc.microservices.searching.exception.handler;


import java.util.Date;

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

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<APIExceptionResponse> defaultExp(final Exception ex, final WebRequest request) {
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIExceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        String errors = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .reduce((error, error2) -> error + ", " + error2).orElse("");
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), "Validation error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIExceptionResponse);
    }
}
