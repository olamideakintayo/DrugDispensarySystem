package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Pharmacist;
import com.boaDispensarySystem.data.models.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository {

    Prescription save(Prescription pharmacist);

    Optional<Prescription> findById(String id);

    Optional<Prescription> findByEmail(String email);

    List<Prescription> findAll();

    Prescription update(Pharmacist pharmacist);

    long count();

    boolean deleteById(String id);
}
