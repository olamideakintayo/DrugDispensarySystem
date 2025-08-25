package com.boaDispensarySystem.services;

import com.boaDispensarySystem.data.models.Admin;
import com.boaDispensarySystem.data.repositories.AdminRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.UpdateAdminRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;
import com.boaDispensarySystem.utils.AdminMapper;
import com.boaDispensarySystem.utils.PasswordHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    private AdminRepositoryImpl adminRepository;
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        adminRepository = mock(AdminRepositoryImpl.class);
        adminService = new AdminServiceImpl(adminRepository);
    }

    @Test
    void createAdmin_shouldSaveAdmin() {
        CreateAdminRequest request = new CreateAdminRequest();
        request.setUsername("admin1");
        request.setPassword("pass123");

        Admin fakeAdmin = new Admin();
        fakeAdmin.setUsername("admin1");
        fakeAdmin.setPassword("hashed");

        try (MockedStatic<AdminMapper> mapperMock = mockStatic(AdminMapper.class);
             MockedStatic<PasswordHash> hashMock = mockStatic(PasswordHash.class)) {

            mapperMock.when(() ->  AdminMapper.mapCreateAdminRequestToAdmin(any()))
                    .thenReturn(fakeAdmin);
            mapperMock.when(() -> AdminMapper.mapAdminToCreateAdminResponse(any()))
                    .thenReturn(new CreateAdminResponse());

            hashMock.when(() -> PasswordHash.hashPassword(anyString())).thenReturn("hashed");

            when(adminRepository.save(any(Admin.class))).thenReturn(fakeAdmin);

            CreateAdminResponse response = adminService.createAdmin(request);

            assertNotNull(response);
            verify(adminRepository).save(any(Admin.class));
        }
    }

    @Test
    void getAdminByUsername_shouldReturnAdmin_whenExists() {
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("hashed");

        when(adminRepository.findByUsername("admin1")).thenReturn(Optional.of(admin));

        try (MockedStatic<AdminMapper> mapperMock = mockStatic(AdminMapper.class)) {
            mapperMock.when(() -> AdminMapper.mapAdminToCreateAdminResponse(any()))
                    .thenReturn(new CreateAdminResponse());

            Optional<CreateAdminResponse> response = adminService.getAdminByUsername("admin1");

            assertTrue(response.isPresent());
            verify(adminRepository).findByUsername("admin1");
        }
    }

    @Test
    void getAdminByUsername_shouldReturnEmpty_whenNotFound() {
        when(adminRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<CreateAdminResponse> response = adminService.getAdminByUsername("unknown");

        assertTrue(response.isEmpty());
        verify(adminRepository).findByUsername("unknown");
    }

    @Test
    void getAllAdmins_shouldReturnList() {
        Admin a1 = new Admin();
        a1.setUsername("admin1");
        Admin a2 = new Admin();
        a2.setUsername("admin2");

        when(adminRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        try (MockedStatic<AdminMapper> mapperMock = mockStatic(AdminMapper.class)) {
            mapperMock.when(() -> AdminMapper.mapAdminToCreateAdminResponse(any()))
                    .thenReturn(new CreateAdminResponse());

            List<CreateAdminResponse> admins = adminService.getAllAdmins();

            assertEquals(2, admins.size());
            verify(adminRepository).findAll();
        }
    }

    @Test
    void deleteAdminByUsername_shouldReturnTrue_whenDeleted() {
        when(adminRepository.deleteByUsername("admin1")).thenReturn(true);

        boolean deleted = adminService.deleteAdminByUsername("admin1");

        assertTrue(deleted);
        verify(adminRepository).deleteByUsername("admin1");
    }

    @Test
    void updateAdmin_shouldUpdate_whenAdminExists() {
        UpdateAdminRequest request = new UpdateAdminRequest();
        request.setUsername("admin1");
        request.setPassword("newPass");

        Admin existingAdmin = new Admin();
        existingAdmin.setUsername("admin1");
        existingAdmin.setPassword("oldPass");

        Admin updatedAdmin = new Admin();
        updatedAdmin.setUsername("admin1");
        updatedAdmin.setPassword("hashed");

        when(adminRepository.findByUsername("admin1")).thenReturn(Optional.of(existingAdmin));
        when(adminRepository.update(any(Admin.class))).thenAnswer(i -> i.getArgument(0));

        try (MockedStatic<AdminMapper> mapperMock = mockStatic(AdminMapper.class);
             MockedStatic<PasswordHash> hashMock = mockStatic(PasswordHash.class)) {

            mapperMock.when(() -> AdminMapper.mapUpdateAdminRequestToAdmin(any(), any()))
                    .thenReturn(updatedAdmin);
            mapperMock.when(() -> AdminMapper.mapAdminToCreateAdminResponse(any()))
                    .thenReturn(new CreateAdminResponse());

            hashMock.when(() -> PasswordHash.hashPassword(anyString())).thenReturn("hashed");

            CreateAdminResponse response = adminService.updateAdmin(request);

            assertNotNull(response);
            verify(adminRepository).findByUsername("admin1");
            verify(adminRepository).update(any(Admin.class));
        }
    }

    @Test
    void updateAdmin_shouldThrowException_whenAdminNotFound() {
        UpdateAdminRequest request = new UpdateAdminRequest();
        request.setUsername("unknown");

        when(adminRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> adminService.updateAdmin(request));

        assertTrue(exception.getMessage().contains("Admin not found with username"));
        verify(adminRepository).findByUsername("unknown");
        verify(adminRepository, never()).update(any());
    }
}
