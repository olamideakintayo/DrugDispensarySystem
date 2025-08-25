package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PharmacistCountException extends RuntimeException {
    public PharmacistCountException(String errorCountingPharmacists, SQLException e) {
        super(errorCountingPharmacists, e);
    }

    public PharmacistCountException() {
    }

    public PharmacistCountException(String message) {
        super(message);
    }
}
