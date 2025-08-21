package com.boaDispensarySystem.services;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;
import com.boaDispensarySystem.utils.DoctorMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DoctorService {

    private final DoctorRepositoryImpl doctorRepository = new DoctorRepositoryImpl();

    // ------------------ CREATE ------------------
    public CreateDoctorResponse createDoctor(CreateDoctorRequest request) {
        Optional<Doctor> existing = doctorRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Doctor with email already exists: " + request.getEmail());
        }

        String id = UUID.randomUUID().toString();
        Doctor doctor = DoctorMapper.mapCreateDoctorRequestToDoctor(request, id);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.mapDoctorToCreateDoctorResponse(savedDoctor);
    }

    // ------------------ FIND BY ID ------------------
    public CreateDoctorResponse getDoctorById(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return DoctorMapper.mapDoctorToCreateDoctorResponse(doctor);
    }

    // ------------------ FIND ALL ------------------
    public List<CreateDoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorMapper::mapDoctorToCreateDoctorResponse)
                .collect(Collectors.toList());
    }

    // ------------------ UPDATE ------------------
    public CreateDoctorResponse updateDoctor(String id, UpdateDoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        Doctor updatedDoctor = DoctorMapper.mapUpdateDoctorRequestToDoctor(doctor, request);
        Doctor savedDoctor = doctorRepository.update(updatedDoctor);

        return DoctorMapper.mapDoctorToCreateDoctorResponse(savedDoctor);
    }

    // ------------------ DELETE ------------------
    public boolean deleteDoctor(String id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        return doctorRepository.deleteById(id);
    }

    // ------------------ COUNT ------------------
    public long countDoctors() {
        return doctorRepository.count();
    }
}
