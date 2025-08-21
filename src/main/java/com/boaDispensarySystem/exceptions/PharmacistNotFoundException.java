package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PharmacistNotFoundException extends RuntimeException {
    public PharmacistNotFoundException(String message, SQLException e) {
        super(message);
    }

    public PharmacistNotFoundException(String message) {
        super(message);
    }
}
