package com.jacobsrene.carrentalapp.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = Map.of(
                "errors", ex.getConstraintViolations().stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .toList()
        );

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(BookingValidationException.class)
    public ResponseEntity<ErrorResponse> handleBookingValidation(BookingValidationException ex) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of("VALIDATION_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("INTERNAL_ERROR", ex.getMessage()));
    }
}
