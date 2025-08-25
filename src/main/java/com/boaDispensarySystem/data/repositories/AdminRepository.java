package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findByUsername(String username);
    List<Admin> findAll();
    boolean deleteByUsername(String username);
    Admin update(Admin admin);
}
