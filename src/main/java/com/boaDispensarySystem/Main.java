package com.boaDispensarySystem;

import com.boaDispensarySystem.data.repositories.DoctorRepository;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        DoctorRepository doctorRepository = new DoctorRepositoryImpl();

        Doctor doctor = new Doctor();
        doctor.setId("D123");
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setSpecialization(Specialization.DERMATOLOGIST);
        doctor.setEmail("johndoe@example.com");
        doctor.setPassword("securepass");

        doctorRepository.save(doctor);
        System.out.println("✅ Doctor saved: " + doctor.getFirstName());


        Optional<Doctor> foundDoctor = doctorRepository.findById("D123");
        if (foundDoctor.isPresent()) {
            System.out.println("🔍 Found doctor: " + foundDoctor.get().getFirstName());
        } else {
            System.out.println("❌ Doctor not found");
        }

    }
}
