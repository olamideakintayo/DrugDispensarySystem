/* package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {


    @Override
    public Prescription save(Prescription prescription) {
        String query = """
                INSERT INTO prescriptions ()
                VALUES (?, ?, ?, ?, ?, ?, ?, NOW())
                ON DUPLICATE KEY UPDATE

                """;
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, prescription.getPrescriptionCode());
            stmt.setString(2, prescription.getPatientId());
            stmt.setString(3, prescription.getDoctorId());
            stmt.setString(4, prescription.getPharmacistId());
            stmt.setObject(5, prescription.getDrugs());
            stmt.setObject(6, prescription.getStatus());

            stmt.executeUpdate();
            return prescription;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving prescription", e);
        }
    }
    @Override
    public Optional<Prescription> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Prescription> findPrescriptionCreatedByDoctorId(String doctorId) {
        return List.of();
    }

    @Override
    public List<Prescription> findPrescriptionDispensedByPharmacistId(String pharmacistId) {
        return List.of();
    }

    @Override
    public List<Prescription> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}

 */