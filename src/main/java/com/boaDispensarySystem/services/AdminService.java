package com.boaDispensarySystem.services;

import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.UpdateAdminRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    CreateAdminResponse createAdmin(CreateAdminRequest request);
    Optional<CreateAdminResponse> getAdminByUsername(String username);
    List<CreateAdminResponse> getAllAdmins();
    boolean deleteAdminByUsername(String username);
    CreateAdminResponse updateAdmin(UpdateAdminRequest request);
}
