package com.boaDispensarySystem.data.repositories;


import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Pharmacist;
import com.boaDispensarySystem.exceptions.PharmacistCreationException;
import com.boaDispensarySystem.exceptions.PharmacistNotFoundException;
import com.boaDispensarySystem.utils.PharmacistMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PharmacistRepositoryImpl implements PharmacistRepository{

    @Override
    public Pharmacist save(Pharmacist pharmacist) {
        String idPrefix = "PH";
        String databaseSize = String.valueOf(count());
        String id = idPrefix+ databaseSize;

        pharmacist.setId(id);

        String sql = "INSERT INTO pharmacists (id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pharmacist.getId());
            ps.setString(2, pharmacist.getFirstName());
            ps.setString(3, pharmacist.getLastName());
            ps.setString(4, pharmacist.getEmail());
            ps.setString(5, pharmacist.getPassword());

            ps.executeUpdate();
            return pharmacist;

        } catch (SQLException e) {
            throw new PharmacistCreationException("Error saving pharmacists", e);
        }
    }

    @Override
    public Optional<Pharmacist> findById(String id) {
        String sql = "SELECT * FROM pharmacists WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(PharmacistMapper.mapResultSetToPharmacist(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new PharmacistNotFoundException("Error finding pharmacist by ID", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM pharmacists WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting pharmacist", e);
        }
        return false;
    }

    @Override
    public Optional<Pharmacist> findByEmail(String email) {
        String sql = "SELECT * FROM pharmacists WHERE email = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(PharmacistMapper.mapResultSetToPharmacist(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding pharmacist by email", e);
        }
    }

    @Override
    public List<Pharmacist> findAll() {
        String sql = "SELECT * FROM pharmacists";
        List<Pharmacist> pharmacist = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pharmacist.add(PharmacistMapper.mapResultSetToPharmacist(rs));
            }
            return pharmacist;

        } catch (SQLException e) {
            throw new PharmacistNotFoundException("Error retrieving all pharmacists", e);
        }
    }
    @Override
    public Pharmacist update(Pharmacist pharmacist) {
        String sql = "UPDATE pharmacists SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pharmacist.getFirstName());
            ps.setString(2, pharmacist.getLastName());
            ps.setString(3, pharmacist.getEmail());
            ps.setString(4, pharmacist.getPassword());
            ps.setString(5, pharmacist.getId());

            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new PharmacistNotFoundException("No pharmacist found with id: " + pharmacist.getId());
            }
            return pharmacist;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating pharmacist", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) AS total FROM pharmacists";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong("total");
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error counting pharmacists", e);
        }
    }


}
