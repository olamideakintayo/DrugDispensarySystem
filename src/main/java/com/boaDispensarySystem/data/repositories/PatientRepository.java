package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Patient save(Patient patient);

    Optional<Patient> findById(String id);

    Optional<Patient> findByEmail(String email);

    List<Patient> findAll();

    boolean deleteById(String id);
}
