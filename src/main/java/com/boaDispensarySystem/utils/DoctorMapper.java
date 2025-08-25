package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorMapper {


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

    public static CreateDoctorResponse mapDoctorToCreateDoctorResponse(Doctor doctor) {
        CreateDoctorResponse response = new CreateDoctorResponse();
        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setEmail(doctor.getEmail());
        response.setSpecialization(Specialization.valueOf(doctor.getSpecialization().name()));
        return response;
    }


    public static Doctor mapUpdateDoctorRequestToDoctor(Doctor existingDoctor, UpdateDoctorRequest request) {
        if (request.getFirstName() != null) existingDoctor.setFirstName(request.getFirstName());
        if (request.getLastName() != null) existingDoctor.setLastName(request.getLastName());
        if (request.getEmail() != null) existingDoctor.setEmail(request.getEmail());
        if (request.getPassword() != null) existingDoctor.setPassword(request.getPassword());
        if (request.getSpecialization() != null) existingDoctor.setSpecialization(request.getSpecialization());
        return existingDoctor;
    }
}
