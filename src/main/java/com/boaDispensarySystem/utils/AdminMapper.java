package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.data.models.Admin;
import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.UpdateAdminRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper {

    public static Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        return admin;
    }


    public static Admin mapCreateAdminRequestToAdmin(CreateAdminRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());
        return admin;
    }


    public static CreateAdminResponse mapAdminToCreateAdminResponse(Admin admin) {
        CreateAdminResponse response = new CreateAdminResponse();
        response.setUsername(admin.getUsername());
        response.setPassword("********");
        return response;
    }


    public static Admin mapUpdateAdminRequestToAdmin(Admin existingAdmin, UpdateAdminRequest request) {
        if (request.getUsername() != null) existingAdmin.setUsername(request.getUsername());
        if (request.getPassword() != null) existingAdmin.setPassword(request.getPassword());
        return existingAdmin;
    }
}
