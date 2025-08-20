package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.data.models.Doctor;

public class DoctorMapper {

    public static Doctor mapCreateRequestToDoctor(CreateDoctorRequest request, String id) {
        return new Doctor(
                id,
                request.getFirstName(),
                request.getLastName(),
                request.getSpecialization(),
                request.getEmail(),
                request.getPassword()
        );
    }
}
