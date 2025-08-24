package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Drug;
import com.boaDispensarySystem.exceptions.*;

import java.sql.*;
import java.util.Optional;

public class DrugRepositoryImpl implements DrugRepository {

    public long count() {
        String sql = "SELECT COUNT(*) AS total FROM drugs";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong("total");
            }
            return 0;

        } catch (SQLException e) {
            throw new DrugCountException("Error counting drugs", e);
        }
    }

    public Drug save(Drug drug) {

        String sql = "INSERT INTO drugs (id, name, type, category, expiry_date, manufacture_date, date_added, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(drug.getId()));
            ps.setString(2, drug.getName());
            ps.setString(3, drug.getType().name());
            ps.setString(4, drug.getCategory().name());
            ps.setDate(5, Date.valueOf(drug.getExpiryDate()));
            ps.setDate(6,Date.valueOf(drug.getManufactureDate()));
            ps.setDate(7, Date.valueOf(drug.getDateAdded().toLocalDate()));
            ps.setInt(8, drug.getQuantity());

            ps.executeUpdate();
            return drug;

        } catch (SQLException e) {
            throw new DrugCreationException("Error saving drug", e);
        }

    }

    @Override
    public boolean deleteByID(int id) {
        String sql = "DELETE FROM drugs WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(id));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DrugDeletionException("Error deleting drug", e);
        }
        return false;
    }

    @Override
    public Optional<Drug> findById(int id) {
        String sql = "SELECT * FROM drugs WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(DrugMapper.mapResultSetToDrug(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DrugNotFoundException("Error finding drug by ID", e);
        }
    }

    @Override
    public Optional<Drug> findByName(String name) {

        String sql = "SELECT * FROM drugs WHERE name = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(DrugMapper.mapResultSetToDrug(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DrugNotFoundException("Error finding drug by name", e);
        }
    }

    private Drug update(Drug drug) {
        String sql = "UPDATE drugs SET name = ?, type = ?, category = ?, expiry_date = ?, manufacture_date = ?, date_added = ?, quantity = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, drug.getName());
            ps.setString(2, drug.getType().name());
            ps.setString(3, drug.getCategory().name());
            ps.setDate(4, Date.valueOf(drug.getExpiryDate()));
            ps.setDate(5, Date.valueOf(drug.getManufactureDate()));
            ps.setDate(6, Date.valueOf(drug.getDateAdded().toLocalDate()));
            ps.setInt(7, drug.getQuantity());

            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new DrugNotFoundException("No doctor found with id: " + drug.getId());
            }
            return drug;

        } catch (SQLException e) {
            throw new DrugUpdateExcpetion("Error updating drug", e);
        }
    }


}





