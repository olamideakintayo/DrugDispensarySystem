package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DrugCountException extends RuntimeException {
    public DrugCountException(String errorCountingDrugs, SQLException e) {
        super(errorCountingDrugs, e);
    }

    public DrugCountException() {
    }

    public DrugCountException(String message) {
        super(message);
    }
}
