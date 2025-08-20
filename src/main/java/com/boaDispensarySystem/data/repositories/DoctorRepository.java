package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository {

    Doctor save(Doctor doctor);

    Optional<Doctor> findById(String id);

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findAll();

    boolean deleteById(String id);
}
