package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DoctorCreationException extends SQLException {
    public DoctorCreationException(String message) {
        super(message);
    }
}
