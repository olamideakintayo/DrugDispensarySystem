package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Pharmacist;

import java.util.List;
import java.util.Optional;

public interface PharmacistRepository {

    Pharmacist save(Pharmacist pharmacist);

    Optional<Pharmacist> findById(String id);

    Optional<Pharmacist> findByEmail(String email);

    List<Pharmacist> findAll();

    Pharmacist update(Pharmacist pharmacist);

    long count();

    boolean deleteById(String id);

}
