package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DrugNotFoundException extends RuntimeException {
    public DrugNotFoundException(String message) {
        super(message);
    }

    public DrugNotFoundException(String errorFindingDrugById, SQLException e) {
        super(errorFindingDrugById, e);
    }
}
