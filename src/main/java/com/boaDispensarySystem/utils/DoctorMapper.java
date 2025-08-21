package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;
import com.boaDispensarySystem.services.dtos.CreateDoctorRequest;
import com.boaDispensarySystem.services.dtos.DoctorResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperUtils {

    // ------------------- ResultSet -> Entity -------------------
    public static Doctor mapResultSetToDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getString("id"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setEmail(rs.getString("email"));
        doctor.setPassword(rs.getString("password"));
        doctor.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
        return doctor;
    }

    // ------------------- DTO -> Entity (Create) -------------------
    public static Doctor mapCreateDoctorRequestToDoctor(CreateDoctorRequest request, String id) {
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPassword(request.getPassword());
        doctor.setSpecialization(request.getSpecialization());
        return doctor;
    }

    // ------------------- Entity -> DTO (Response) -------------------
    public static DoctorResponse mapDoctorToDoctorResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getEmail(),
                doctor.getSpecialization()

        );
    }

    // ------------------- DTO -> Entity (Update) -------------------
    public static Doctor mapUpdateDoctorRequestToDoctor(Doctor existingDoctor, CreateDoctorRequest request) {
        existingDoctor.setFirstName(request.getFirstName());
        existingDoctor.setLastName(request.getLastName());
        existingDoctor.setEmail(request.getEmail());
        existingDoctor.setPassword(request.getPassword());
        existingDoctor.setSpecialization(request.getSpecialization());
        return existingDoctor;
    }
}