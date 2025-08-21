package com.boaDispensarySystem.exceptions;

import java.sql.SQLException;

public class SQLNullException extends SQLException {
    public SQLNullException(String message) {
        super(message);
    }
}
