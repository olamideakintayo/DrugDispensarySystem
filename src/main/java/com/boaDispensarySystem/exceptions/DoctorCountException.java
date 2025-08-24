package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DoctorCountException extends RuntimeException {
    public DoctorCountException(String s) {
        super(s);
    }

    public DoctorCountException(String errorCountingDoctors, SQLException e) {
        super(errorCountingDoctors, e);
    }
}
