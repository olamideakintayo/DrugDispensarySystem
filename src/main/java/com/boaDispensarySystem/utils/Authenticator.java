package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.controllers.AdminController;
import com.boaDispensarySystem.controllers.DoctorController;
import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;

import java.util.Optional;

public class Authenticator {

    private final AdminController adminController;
    private final DoctorController doctorController;

    public Authenticator(AdminController adminController, DoctorController doctorController) {
        this.adminController = adminController;
        this.doctorController = doctorController;
    }

    // ===== REGISTER =====
    public CreateAdminResponse registerAdmin(CreateAdminRequest request) {
        // Hash password before saving
        request.setPassword(PasswordHash.hashPassword(request.getPassword()));
        return adminController.createAdmin(request);
    }

    public CreateDoctorResponse registerDoctor(CreateDoctorRequest request) {
        // Hash password before saving
        request.setPassword(PasswordHash.hashPassword(request.getPassword()));
        return doctorController.createDoctor(request);
    }

    // ===== LOGIN =====
    public boolean loginAdmin(String username, String password) {
        Optional<CreateAdminResponse> optionalAdmin =
                adminController.getAdminByUsername(username);
        return optionalAdmin.map(admin -> PasswordHash.verifyPassword(password, admin.getPassword()))
                .orElse(false);
    }

    public boolean loginDoctor(String email, String password) {
        // Look up doctor by email
        Optional<CreateDoctorResponse> optionalDoctor =
                doctorController.getAllDoctors().stream()
                        .filter(doc -> doc.getEmail().equalsIgnoreCase(email))
                        .findFirst();

        return optionalDoctor.map(doc -> PasswordHash.verifyPassword(password, doc.getPassword()))
                .orElse(false);
    }
}
