package com.example.questionbank.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Converts domain and application exceptions into HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles invalid input errors.
     *
     * @param exception thrown exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", exception.getMessage()));
    }

    /**
     * Handles business rule violations.
     *
     * @param exception thrown exception
     * @return HTTP 409 response
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleConflict(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", exception.getMessage()));
    }
}
