package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

    public class DoctorDeletionException extends RuntimeException {
    public DoctorDeletionException(String errorDeletingDoctor, SQLException e) {
        super(errorDeletingDoctor, e);
    }
}
