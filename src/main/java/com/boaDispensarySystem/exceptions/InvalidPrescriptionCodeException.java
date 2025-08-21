package com.boaDispensarySystem.exceptions;

public class InvalidPrescriptionCodeException extends RuntimeException {
    public InvalidPrescriptionCodeException(String message) {
        super(message);
    }
}
