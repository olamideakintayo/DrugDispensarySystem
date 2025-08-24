package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DoctorCreationException extends RuntimeException {
    public DoctorCreationException(String message) {
        super(message);
    }

    public DoctorCreationException(String errorSavingDoctor, SQLException e) {
        super(errorSavingDoctor, e);
    }
}
