package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorRepositoryImpl implements DoctorRepository {

    @Override
    public Doctor save(Doctor doctor) {
        String query = """
                INSERT INTO doctors (id, first_name, last_name, specialization, email, password)
                VALUES (?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    first_name = VALUES(first_name),
                    last_name = VALUES(last_name),
                    specialization = VALUES(specialization),
                    email = VALUES(email),
                    password = VALUES(password)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, doctor.getId());
            stmt.setString(2, doctor.getFirstName());
            stmt.setString(3, doctor.getLastName());
            stmt.setString(4, doctor.getSpecialization().name()); // store enum as string
            stmt.setString(5, doctor.getEmail());
            stmt.setString(6, doctor.getPassword());

            stmt.executeUpdate();
            return doctor;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving doctor", e);
        }
    }

    @Override
    public Optional<Doctor> findById(String id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);  // ✅ Make sure you use setString

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(rs.getString("id"));
                    doctor.setFirstName(rs.getString("first_name"));
                    doctor.setLastName(rs.getString("last_name"));
                    doctor.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setPassword(rs.getString("password"));
                    return Optional.of(doctor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding doctor by id", e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Doctor> findByEmail(String email) {
        String query = "SELECT * FROM doctors WHERE email = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToDoctor(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding doctor by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                doctors.add(mapRowToDoctor(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all doctors", e);
        }
        return doctors;
    }

    @Override
    public boolean deleteById(String id) {
        String query = "DELETE FROM doctors WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting doctor", e);
        }
    }

    private Doctor mapRowToDoctor(ResultSet rs) throws SQLException {
        return new Doctor(
                rs.getString("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Specialization.valueOf(rs.getString("specialization")),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}

