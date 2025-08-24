package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DoctorUpdateException extends RuntimeException {
    public DoctorUpdateException(String errorUpdatingDoctor, SQLException e) {
        super(errorUpdatingDoctor, e);
    }
}
