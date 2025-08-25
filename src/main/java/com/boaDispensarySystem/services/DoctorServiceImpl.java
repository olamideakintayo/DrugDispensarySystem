package com.boaDispensarySystem.services;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;
import com.boaDispensarySystem.utils.DoctorMapper;
import com.boaDispensarySystem.utils.PasswordHash;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepositoryImpl doctorRepository;

    public DoctorServiceImpl(DoctorRepositoryImpl doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public CreateDoctorResponse createDoctor(CreateDoctorRequest request) {
        // Check if email exists
        Optional<Doctor> existing = doctorRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Doctor with email already exists: " + request.getEmail());
        }

        // Generate UUID for ID
        String id = UUID.randomUUID().toString();
        Doctor doctor = DoctorMapper.mapCreateDoctorRequestToDoctor(request, id);

        // Hash password
        doctor.setPassword(PasswordHash.hashPassword(doctor.getPassword()));

        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.mapDoctorToCreateDoctorResponse(savedDoctor);
    }

    @Override
    public CreateDoctorResponse getDoctorById(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return DoctorMapper.mapDoctorToCreateDoctorResponse(doctor);
    }

    @Override
    public List<CreateDoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorMapper::mapDoctorToCreateDoctorResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CreateDoctorResponse updateDoctor(String id, UpdateDoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        Doctor updatedDoctor = DoctorMapper.mapUpdateDoctorRequestToDoctor(doctor, request);

        if (updatedDoctor.getPassword() != null) {
            updatedDoctor.setPassword(PasswordHash.hashPassword(updatedDoctor.getPassword()));
        }

        Doctor savedDoctor = doctorRepository.update(updatedDoctor);
        return DoctorMapper.mapDoctorToCreateDoctorResponse(savedDoctor);
    }

    @Override
    public boolean deleteDoctor(String id) {
        return doctorRepository.deleteById(id);
    }

    @Override
    public long countDoctors() {
        return doctorRepository.count();
    }
}
