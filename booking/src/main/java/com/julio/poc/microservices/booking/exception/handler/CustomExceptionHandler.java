package com.julio.poc.microservices.booking.exception.handler;


import feign.FeignException;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;

import com.julio.poc.microservices.booking.exception.ConflictElementException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<APIExceptionResponse> defaultExp(final Exception ex, final WebRequest request) {
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIExceptionResponse);
    }

    @ExceptionHandler(ConflictElementException.class)
    private ResponseEntity<APIExceptionResponse> conflictExp(final Exception ex, final WebRequest request) {
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(APIExceptionResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<APIExceptionResponse> notFoundExp(final Exception ex, final WebRequest request) {
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIExceptionResponse);
    }

    @ExceptionHandler(FeignException.class)
    private ResponseEntity<APIExceptionResponse> feignExp(final FeignException ex, final WebRequest request) {
        ex.printStackTrace();
        log.error("Invalid response of endpoint {} due {}", request.getContextPath(), ex.getMessage());
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(ex.status()).body(APIExceptionResponse);
    }

    @ExceptionHandler(DecodeException.class)
    private ResponseEntity<APIExceptionResponse> feignDecodeExp(final FeignException ex, final WebRequest request) {
        ex.printStackTrace();
        log.error("Impossible to decode the response of url {} due {}", request.getContextPath(), ex.getMessage());
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
