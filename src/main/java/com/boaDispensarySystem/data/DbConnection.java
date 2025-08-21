package com.boaDispensarySystem.data;


import com.boaDispensarySystem.exceptions.SQLNullException;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        // Debug print to confirm values are loaded
        System.out.println("🔍 Loaded DB_URL = " + url);
        System.out.println("🔍 Loaded DB_USER = " + user);
        // Don’t print password for security in real apps
        System.out.println("🔍 Loaded DB_PASSWORD = " + (password != null ? "****" : "null"));

        if (url == null || user == null) {
            throw new SQLNullException("Database credentials not loaded from .env file");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
