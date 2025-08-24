package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class PatientsRetrievalException extends RuntimeException {
    public PatientsRetrievalException() {
    }

    public PatientsRetrievalException(String message) {
        super(message);
    }

    public PatientsRetrievalException(String errorRetrievingAllPatients, SQLException e) {
        super(errorRetrievingAllPatients, e);
    }
}
