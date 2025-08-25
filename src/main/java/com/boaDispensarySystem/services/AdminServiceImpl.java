package com.boaDispensarySystem.services;

import com.boaDispensarySystem.data.models.Admin;
import com.boaDispensarySystem.data.repositories.AdminRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.UpdateAdminRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;
import com.boaDispensarySystem.utils.AdminMapper;
import com.boaDispensarySystem.utils.PasswordHash;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminServiceImpl implements AdminService{


    private final AdminRepositoryImpl adminRepository;

    public AdminServiceImpl(AdminRepositoryImpl adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public CreateAdminResponse createAdmin(CreateAdminRequest request) {
        Admin admin = AdminMapper.mapCreateAdminRequestToAdmin(request);
        admin.setPassword(PasswordHash.hashPassword(request.getPassword()));

        adminRepository.save(admin);
        return AdminMapper.mapAdminToCreateAdminResponse(admin);
    }




    @Override
    public Optional<CreateAdminResponse> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .map(AdminMapper::mapAdminToCreateAdminResponse);
    }

    @Override
    public List<CreateAdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(AdminMapper::mapAdminToCreateAdminResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteAdminByUsername(String username) {
        return adminRepository.deleteByUsername(username);
    }

    @Override
    public CreateAdminResponse updateAdmin(UpdateAdminRequest request) {
        Optional<Admin> existingAdminOpt = adminRepository.findByUsername(request.getUsername());

        if (existingAdminOpt.isEmpty()) {
            throw new RuntimeException("Admin not found with username: " + request.getUsername());
        }

        Admin existingAdmin = existingAdminOpt.get();
        Admin updatedAdmin = AdminMapper.mapUpdateAdminRequestToAdmin(existingAdmin, request);

        if (updatedAdmin.getPassword() != null) {
            updatedAdmin.setPassword(PasswordHash.hashPassword(updatedAdmin.getPassword()));
        }

        Admin savedAdmin = adminRepository.update(updatedAdmin);

        return AdminMapper.mapAdminToCreateAdminResponse(savedAdmin);
    }


}
