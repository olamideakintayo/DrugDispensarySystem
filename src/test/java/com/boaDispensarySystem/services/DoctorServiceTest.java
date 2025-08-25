package com.boaDispensarySystem.services;

import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;
import com.boaDispensarySystem.utils.DoctorMapper;
import com.boaDispensarySystem.utils.PasswordHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    private DoctorRepositoryImpl doctorRepository;
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorRepository = mock(DoctorRepositoryImpl.class);
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void createDoctor_shouldSaveDoctor_whenEmailNotExists() {
        CreateDoctorRequest request = new CreateDoctorRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPassword("pass123");

        when(doctorRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());

        Doctor fakeDoctor = new Doctor();
        fakeDoctor.setId("123");
        fakeDoctor.setFirstName("John");
        fakeDoctor.setLastName("Doe");
        fakeDoctor.setSpecialization(Specialization.DERMATOLOGY);
        fakeDoctor.setPassword("hashed");

        try (MockedStatic<DoctorMapper> mapperMock = mockStatic(DoctorMapper.class);
             MockedStatic<PasswordHash> hashMock = mockStatic(PasswordHash.class)) {

            mapperMock.when(() -> DoctorMapper.mapCreateDoctorRequestToDoctor(any(), anyString()))
                    .thenReturn(fakeDoctor);
            mapperMock.when(() -> DoctorMapper.mapDoctorToCreateDoctorResponse(any()))
                    .thenReturn(new CreateDoctorResponse());

            hashMock.when(() -> PasswordHash.hashPassword(anyString())).thenReturn("hashed");

            when(doctorRepository.save(any(Doctor.class))).thenReturn(fakeDoctor);

            CreateDoctorResponse response = doctorService.createDoctor(request);

            assertNotNull(response);
            verify(doctorRepository).findByEmail("john@example.com");
            verify(doctorRepository).save(any(Doctor.class));
        }
    }

    @Test
    void createDoctor_shouldThrowException_whenEmailExists() {
        CreateDoctorRequest request = new CreateDoctorRequest();
        request.setEmail("existing@example.com");

        Doctor existingDoctor = new Doctor();
        existingDoctor.setEmail("existing@example.com");

        when(doctorRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingDoctor));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> doctorService.createDoctor(request));

        assertTrue(exception.getMessage().contains("Doctor with email already exists"));
        verify(doctorRepository).findByEmail("existing@example.com");
        verify(doctorRepository, never()).save(any());
    }

    @Test
    void getDoctorById_shouldReturnDoctor_whenExists() {
        Doctor doctor = new Doctor();
        doctor.setId("1");
        doctor.setFirstName("Alice");
        doctor.setLastName("Smith");
        doctor.setSpecialization(Specialization.DERMATOLOGIST);

        when(doctorRepository.findById("1")).thenReturn(Optional.of(doctor));

        try (MockedStatic<DoctorMapper> mapperMock = mockStatic(DoctorMapper.class)) {
            mapperMock.when(() -> DoctorMapper.mapDoctorToCreateDoctorResponse(any()))
                    .thenReturn(new CreateDoctorResponse());

            CreateDoctorResponse response = doctorService.getDoctorById("1");

            assertNotNull(response);
            verify(doctorRepository).findById("1");
        }
    }

    @Test
    void getDoctorById_shouldThrowException_whenNotFound() {
        when(doctorRepository.findById("99")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> doctorService.getDoctorById("99"));

        assertTrue(exception.getMessage().contains("Doctor not found with id"));
        verify(doctorRepository).findById("99");
    }

    @Test
    void getAllDoctors_shouldReturnList() {
        Doctor d1 = new Doctor();
        d1.setId("1");
        d1.setFirstName("John");
        d1.setLastName("Doe");
        d1.setSpecialization(Specialization.DERMATOLOGY);

        Doctor d2 = new Doctor();
        d2.setId("2");
        d2.setFirstName("Jane");
        d2.setLastName("Smith");
        d2.setSpecialization(Specialization.DERMATOLOGY);

        when(doctorRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        try (MockedStatic<DoctorMapper> mapperMock = mockStatic(DoctorMapper.class)) {
            mapperMock.when(() -> DoctorMapper.mapDoctorToCreateDoctorResponse(any()))
                    .thenReturn(new CreateDoctorResponse());

            List<CreateDoctorResponse> result = doctorService.getAllDoctors();

            assertEquals(2, result.size());
            verify(doctorRepository).findAll();
        }
    }

    @Test
    void updateDoctor_shouldUpdate_whenDoctorExists() {
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId("1");
        existingDoctor.setPassword("oldPass");
        existingDoctor.setSpecialization(Specialization.DERMATOLOGY);

        UpdateDoctorRequest request = new UpdateDoctorRequest();
        request.setPassword("newPass");

        when(doctorRepository.findById("1")).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.update(any(Doctor.class))).thenAnswer(i -> i.getArgument(0));

        try (MockedStatic<DoctorMapper> mapperMock = mockStatic(DoctorMapper.class);
             MockedStatic<PasswordHash> hashMock = mockStatic(PasswordHash.class)) {

            mapperMock.when(() -> DoctorMapper.mapUpdateDoctorRequestToDoctor(any(), any()))
                    .thenReturn(existingDoctor);
            mapperMock.when(() -> DoctorMapper.mapDoctorToCreateDoctorResponse(any()))
                    .thenReturn(new CreateDoctorResponse());

            hashMock.when(() -> PasswordHash.hashPassword(anyString())).thenReturn("hashed");

            CreateDoctorResponse response = doctorService.updateDoctor("1", request);

            assertNotNull(response);
            verify(doctorRepository).findById("1");
            verify(doctorRepository).update(any(Doctor.class));
        }
    }

    @Test
    void updateDoctor_shouldThrowException_whenDoctorNotFound() {
        UpdateDoctorRequest request = new UpdateDoctorRequest();
        when(doctorRepository.findById("99")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> doctorService.updateDoctor("99", request));

        assertTrue(exception.getMessage().contains("Doctor not found with id"));
        verify(doctorRepository).findById("99");
        verify(doctorRepository, never()).update(any());
    }

    @Test
    void deleteDoctor_shouldDelete_whenDoctorExists() {
        Doctor doctor = new Doctor();
        doctor.setId("1");

        when(doctorRepository.findById("1")).thenReturn(Optional.of(doctor));
        when(doctorRepository.deleteById("1")).thenReturn(true);

        boolean deleted = doctorService.deleteDoctor("1");

        assertTrue(deleted);
        verify(doctorRepository).findById("1");
        verify(doctorRepository).deleteById("1");
    }

    @Test
    void deleteDoctor_shouldThrowException_whenDoctorNotFound() {
        when(doctorRepository.findById("99")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> doctorService.deleteDoctor("99"));

        assertTrue(exception.getMessage().contains("Doctor not found with id"));
        verify(doctorRepository).findById("99");
        verify(doctorRepository, never()).deleteById(any());
    }

    @Test
    void countDoctors_shouldReturnCount() {
        when(doctorRepository.count()).thenReturn(5L);

        long count = doctorService.countDoctors();

        assertEquals(5L, count);
        verify(doctorRepository).count();
    }
}

