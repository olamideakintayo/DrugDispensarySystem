package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PatientDeletionException extends RuntimeException {
    public PatientDeletionException() {
    }

    public PatientDeletionException(String message) {
        super(message);
    }

    public PatientDeletionException(String errorDeletingPatient, SQLException e) {
        super(errorDeletingPatient, e);
    }
}
