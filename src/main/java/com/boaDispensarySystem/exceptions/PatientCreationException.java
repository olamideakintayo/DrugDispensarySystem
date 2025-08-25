package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PatientCreationException extends RuntimeException {
    public PatientCreationException(String errorSavingPatient, SQLException e) {
        super(errorSavingPatient, e);
    }
}
