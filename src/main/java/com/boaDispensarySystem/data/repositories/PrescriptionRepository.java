package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Pharmacist;
import com.boaDispensarySystem.data.models.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository {

    Prescription save(Prescription prescription);

    Optional<Prescription> findById(String id);

    List<Prescription> findPrescriptionCreatedByDoctorId(String doctorId);

    List<Prescription> findPrescriptionDispensedByPharmacistId(String pharmacistId);

    List<Prescription> findAll();

    boolean deleteById(String id);
}
