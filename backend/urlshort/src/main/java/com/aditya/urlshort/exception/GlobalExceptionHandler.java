package com.aditya.urlshort.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleShortUrlNotFound(
            ShortUrlNotFoundException ex) {

        ErrorResponse errorResponse =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        ex.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(AliasAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleAliasAlreadyExists(
            AliasAlreadyExistsException ex) {

        ErrorResponse errorResponse =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        ex.getMessage()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
}