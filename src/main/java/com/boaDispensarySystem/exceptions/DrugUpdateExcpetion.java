package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class DrugUpdateExcpetion extends RuntimeException {
    public DrugUpdateExcpetion(String errorUpdatingDrug, SQLException e) {
        super(errorUpdatingDrug, e);
    }
}
