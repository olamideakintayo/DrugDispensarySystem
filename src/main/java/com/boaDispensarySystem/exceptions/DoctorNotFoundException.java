package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String message) {
        super(message);
    }

    public DoctorNotFoundException(String errorFindingDoctorById, SQLException e) {
        super(errorFindingDoctorById, e);
    }
}
