package com.jacobsrene.carrentalapp.exception;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message, Throwable cause) { super(message, cause); }

    public ExternalServiceException(String message) {
    }
}
