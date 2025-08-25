package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DrugCreationException extends RuntimeException {
    public DrugCreationException() {
    }

    public DrugCreationException(String message) {
        super(message);
    }

    public DrugCreationException(String errorSavingDrug, SQLException e) {
        super(errorSavingDrug, e);
    }
}
