package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
    }

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(String errorFindingPatientById, SQLException e) {
        super(errorFindingPatientById, e);
    }
}
