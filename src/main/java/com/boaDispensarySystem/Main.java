package com.boaDispensarySystem;

import com.boaDispensarySystem.data.models.Patient;
import com.boaDispensarySystem.data.repositories.DoctorRepository;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;
import com.boaDispensarySystem.data.repositories.PatientRepository;
import com.boaDispensarySystem.data.repositories.PatientRepositoryImpl;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        DoctorRepository doctorRepository = new DoctorRepositoryImpl();
        PatientRepository patientRepository = new PatientRepositoryImpl();

        Doctor doctor = new Doctor();
        doctor.setId("D123");
        doctor.setFirstName("Augustine");
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

//        Patient patient = new Patient();
//        patient.setId("P456");
//        patient.setFirstName("Jane");
//        patient.setLastName("Doe");
//        patient.setEmail("j.doe@gmail.com");
//        patient.setAge("12");
//        patient.setGender("Female");
//        patientRepository.save(patient);
//        System.out.println("✅ Patient saved:" + patient.getFirstName());


    }
}
