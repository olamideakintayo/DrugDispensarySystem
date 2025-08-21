package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Patient;
import com.boaDispensarySystem.data.models.Specialization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PatientRepositoryImpl implements PatientRepository {


    @Override
    public Patient save(Patient patient) {
        String query = """
                INSERT INTO patients (id, first_name, last_name, age, gender, email)
                VALUES (?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    first_name = VALUES(first_name),
                    last_name = VALUES(last_name),
                    age = VALUES(age),
                    gender = VALUES(gender),
                    email = VALUES(email)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, patient.getId());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setString(4, patient.getAge());
            stmt.setString(5, patient.getGender());
            stmt.setString(6, patient.getEmail());

            stmt.executeUpdate();
            return patient;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving patient", e);
        }
    }

    @Override
    public Optional<Patient> findById(String id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setId(rs.getString("id"));
                    patient.setFirstName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setAge(rs.getString("age"));
                    patient.setGender(rs.getString("gender"));
                    patient.setEmail(rs.getString("email"));
                    return Optional.of(patient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding patient by id", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        String query = "SELECT * FROM patients WHERE email = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToPatient(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding patient by email", e);
        }
        return Optional.empty();
    }

    private Patient mapRowToPatient(ResultSet rs) {
        return null;
    }

    @Override
    public List<Patient> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
