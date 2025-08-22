package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PharmacistCreationException extends RuntimeException {
    public PharmacistCreationException(String message, SQLException e) {
        super(message);
    }
}
