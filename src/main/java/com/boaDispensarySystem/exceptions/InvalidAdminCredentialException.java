package com.boaDispensarySystem.exceptions;

public class InvalidAdminCredentialException extends RuntimeException {
    public InvalidAdminCredentialException(String message) {
        super(message);
    }
}
