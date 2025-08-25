package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.exceptions.*;
import com.boaDispensarySystem.utils.DoctorMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorRepositoryImpl implements DoctorRepository {

    @Override
    public Doctor save(Doctor doctor) {
        String sql = "INSERT INTO doctors (id, first_name, last_name, email, password, specialization) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doctor.getId());
            ps.setString(2, doctor.getFirstName());
            ps.setString(3, doctor.getLastName());
            ps.setString(4, doctor.getEmail());
            ps.setString(5, doctor.getPassword());
            ps.setString(6, doctor.getSpecialization());

            ps.executeUpdate();
            return doctor;

        } catch (SQLException e) {
            throw new DoctorCreationException("Error saving doctor", e);
        }
    }

    @Override
    public Optional<Doctor> findById(String id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(DoctorMapper.mapResultSetToDoctor(rs));
            return Optional.empty();

        } catch (SQLException e) {
            throw new DoctorNotFoundException("Error finding doctor by ID", e);
        }
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        String sql = "SELECT * FROM doctors WHERE email = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(DoctorMapper.mapResultSetToDoctor(rs));
            return Optional.empty();

        } catch (SQLException e) {
            throw new DoctorNotFoundException("Error finding doctor by email", e);
        }
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctors";
        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) doctors.add(DoctorMapper.mapResultSetToDoctor(rs));
            return doctors;

        } catch (SQLException e) {
            throw new DoctorNotFoundException("Error retrieving all doctors", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DoctorDeletionException("Error deleting doctor", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) AS total FROM doctors";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getLong("total");
            return 0;

        } catch (SQLException e) {
            throw new DoctorCountException("Error counting doctors", e);
        }
    }

    @Override
    public Doctor update(Doctor doctor) {
        String sql = "UPDATE doctors SET first_name = ?, last_name = ?, email = ?, password = ?, specialization = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doctor.getFirstName());
            ps.setString(2, doctor.getLastName());
            ps.setString(3, doctor.getEmail());
            ps.setString(4, doctor.getPassword());
            ps.setString(5, doctor.getSpecialization());
            ps.setString(6, doctor.getId());

            if (ps.executeUpdate() == 0) {
                throw new DoctorNotFoundException("No doctor found with id: " + doctor.getId());
            }
            return doctor;

        } catch (SQLException e) {
            throw new DoctorUpdateException("Error updating doctor", e);
        }
    }
}
