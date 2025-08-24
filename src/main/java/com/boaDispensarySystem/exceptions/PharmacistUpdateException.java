package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PharmacistUpdateException extends RuntimeException {
    public PharmacistUpdateException() {
    }

    public PharmacistUpdateException(String message) {
        super(message);
    }

    public PharmacistUpdateException(String errorUpdatingPharmacist, SQLException e) {
        super(errorUpdatingPharmacist, e);
    }
}
