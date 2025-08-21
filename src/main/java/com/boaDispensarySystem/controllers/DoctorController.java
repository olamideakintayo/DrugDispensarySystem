package com.boaDispensarySystem.controllers;

import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;
import com.boaDispensarySystem.services.DoctorService;

import java.util.List;

public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController() {
        this.doctorService = new DoctorService();
    }

    public CreateDoctorResponse createDoctor(CreateDoctorRequest request) {
        return doctorService.createDoctor(request);
    }


    public CreateDoctorResponse getDoctorById(String id) {
        return doctorService.getDoctorById(id);
    }


    public List<CreateDoctorResponse> getAllDoctors() {
        return doctorService.getAllDoctors();
    }


    public CreateDoctorResponse updateDoctor(String id, UpdateDoctorRequest request) {
        return doctorService.updateDoctor(id, request);
    }


    public boolean deleteDoctor(String id) {
        return doctorService.deleteDoctor(id);
    }
}
