package com.boaDispensarySystem.services;

import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;

import java.util.List;

public interface DoctorService {

    CreateDoctorResponse createDoctor(CreateDoctorRequest request);

    CreateDoctorResponse getDoctorById(String id);

    List<CreateDoctorResponse> getAllDoctors();

    CreateDoctorResponse updateDoctor(String id, UpdateDoctorRequest request);

    boolean deleteDoctor(String id);

    long countDoctors();
}
