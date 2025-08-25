package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DrugDeletionException extends RuntimeException {
    public DrugDeletionException(String errorDeletingDrug, SQLException e) {
        super(errorDeletingDrug, e);
    }

    public DrugDeletionException() {
    }

    public DrugDeletionException(String message) {
        super(message);
    }
}
