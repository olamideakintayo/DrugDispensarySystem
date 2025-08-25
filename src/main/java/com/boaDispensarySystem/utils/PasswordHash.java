package com.boaDispensarySystem.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {

    // Hash a plain password
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify a plain password against a hashed password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null || hashedPassword.isEmpty()) {
            return false; // never throw NPE
        }
        if (!hashedPassword.startsWith("$2")) { // ensure it's a BCrypt hash
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
