package com.julio.poc.microservices.booking.exception.handler;


import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.julio.poc.microservices.booking.exception.ValidationException;
import feign.FeignException;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<APIExceptionResponse> defaultExp(final Exception ex, final WebRequest request) {
        log.error("Generic Exception on endpoint {} due {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(),
                "Unfortunately was not possible to process your operation. " +
                        "I'm really embarrassed, but our team is running to fix it as soon as possible. " +
                        "Sorry and try again later!",
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIExceptionResponse);
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<APIExceptionResponse> validationException(final Exception ex, final WebRequest request) {
        log.error("ConflictElementException on endpoint {} due {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIExceptionResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<APIExceptionResponse> noSuchElementException(final Exception ex, final WebRequest request) {
        log.error("NoSuchElementException on endpoint {} due {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIExceptionResponse);
    }

    @ExceptionHandler(CannotAcquireLockException.class)
    private ResponseEntity<APIExceptionResponse> cannotAcquireLockException(final Exception ex, final WebRequest request) {
        log.error("The transaction was refused since we already have a lock on endpoint {}. Details: {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(),
                "Transaction Locked, try again.", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(APIExceptionResponse);
    }

    @ExceptionHandler(JpaSystemException.class)
    private ResponseEntity<APIExceptionResponse> jpaSystemException(final Exception ex, final WebRequest request) {
        log.error("The transaction was refused since we already have a lock on endpoint {}. Details: {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(),
                "Transaction Locked.", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(APIExceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<APIExceptionResponse> illegalArgumentException(final Exception ex, final WebRequest request) {
        log.error("IllegalArgumentException on endpoint {} due {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIExceptionResponse);
    }

    @ExceptionHandler(FeignException.class)
    private ResponseEntity<APIExceptionResponse> feignException(final FeignException ex, final WebRequest request) {
        log.error("Invalid response of endpoint {} due {}", request.getContextPath(), ex);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(ex.status()).body(APIExceptionResponse);
    }

    @ExceptionHandler(DecodeException.class)
    private ResponseEntity<APIExceptionResponse> feignDecodeException(final FeignException ex, final WebRequest request) {
        log.error("Impossible to decode the response of url {} due {}", request.getContextPath(), ex);
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
        log.error("Validation Errors on endpoint {} due {}", request.getContextPath(), errors);
        APIExceptionResponse APIExceptionResponse = new APIExceptionResponse(new Date(), "Validation error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIExceptionResponse);
    }
}
