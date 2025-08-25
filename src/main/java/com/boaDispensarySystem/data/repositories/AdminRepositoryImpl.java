package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminRepositoryImpl implements AdminRepository {

    @Override
    public Admin save(Admin admin) {
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());

            ps.executeUpdate();
            return admin;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving admin", e);
        }
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        String sql = "SELECT * FROM admins WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return Optional.of(admin);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding admin by username", e);
        }
    }

    @Override
    public List<Admin> findAll() {
        String sql = "SELECT * FROM admins";
        List<Admin> admins = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admins.add(admin);
            }
            return admins;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all admins", e);
        }
    }

    @Override
    public boolean deleteByUsername(String username) {
        String sql = "DELETE FROM admins WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting admin", e);
        }
    }
    @Override
    public Admin update(Admin admin) {
        String sql = "UPDATE admins SET password = ? WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getPassword());
            ps.setString(2, admin.getUsername());

            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new RuntimeException("No admin found with username: " + admin.getUsername());
            }
            return admin;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating admin", e);
        }
    }
}
