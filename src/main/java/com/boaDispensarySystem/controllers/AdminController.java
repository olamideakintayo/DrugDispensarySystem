package com.boaDispensarySystem.controllers;

import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.UpdateAdminRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;
import com.boaDispensarySystem.services.AdminService;

import java.util.List;
import java.util.Optional;

public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public CreateAdminResponse createAdmin(CreateAdminRequest request) {
        return adminService.createAdmin(request);
    }

    public Optional<CreateAdminResponse> getAdminByUsername(String username) {
        return adminService.getAdminByUsername(username);
    }

    public List<CreateAdminResponse> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    public boolean deleteAdminByUsername(String username) {
        return adminService.deleteAdminByUsername(username);
    }

    public CreateAdminResponse updateAdmin(UpdateAdminRequest request) {
        return adminService.updateAdmin(request);
    }
}
