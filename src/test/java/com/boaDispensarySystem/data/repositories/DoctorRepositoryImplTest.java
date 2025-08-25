package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Doctor;
import com.boaDispensarySystem.data.models.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorRepositoryImplTest {

    private DoctorRepositoryImpl doctorRepository;

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        doctorRepository = new DoctorRepositoryImpl();

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void save_shouldReturnDoctor() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@example.com");
        doctor.setPassword("pass123");
        doctor.setSpecialization("Cardiology");

        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getLong("total")).thenReturn(5L);

            Doctor result = doctorRepository.save(doctor);

            assertNotNull(result);
            assertEquals("DR5", result.getId());
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void findById_shouldReturnDoctor_whenExists() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("id")).thenReturn("DR1");
            when(mockResultSet.getString("first_name")).thenReturn("Alice");
            when(mockResultSet.getString("last_name")).thenReturn("Smith");
            when(mockResultSet.getString("email")).thenReturn("alice@example.com");
            when(mockResultSet.getString("password")).thenReturn("pass");
            when(mockResultSet.getString("specialization")).thenReturn("DERMATOLOGY");

            Optional<Doctor> result = doctorRepository.findById("DR1");

            assertTrue(result.isPresent());
            assertEquals("DR1", result.get().getId());
        }
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            Optional<Doctor> result = doctorRepository.findById("DR99");

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void findAll_shouldReturnList() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getString("id")).thenReturn("DR1", "DR2");
            when(mockResultSet.getString("first_name")).thenReturn("John", "Jane");
            when(mockResultSet.getString("last_name")).thenReturn("Doe", "Smith");
            when(mockResultSet.getString("email")).thenReturn("j1@example.com", "j2@example.com");
            when(mockResultSet.getString("password")).thenReturn("p1", "p2");
            when(mockResultSet.getString("specialization")).thenReturn("CARDIOLOGY", "DERMATOLOGY");

            List<Doctor> doctors = doctorRepository.findAll();

            assertEquals(2, doctors.size());
            assertEquals("DR1", doctors.get(0).getId());
            assertEquals("DR2", doctors.get(1).getId());
        }
    }

    @Test
    void deleteById_shouldCallExecuteUpdate() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean result = doctorRepository.deleteById("DR1");

            assertFalse(result);
            verify(mockPreparedStatement).setString(1, "DR1");
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void count_shouldReturnTotal() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getLong("total")).thenReturn(10L);

            long total = doctorRepository.count();

            assertEquals(10L, total);
        }
    }

    @Test
    void update_shouldReturnDoctor_whenUpdated() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId("DR1");
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@example.com");
        doctor.setPassword("pass123");
        doctor.setSpecialization("Virology");

        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            Doctor result = doctorRepository.update(doctor);

            assertEquals("DR1", result.getId());
            assertEquals("John", result.getFirstName());
            verify(mockPreparedStatement).executeUpdate();
        }
    }


    @Test
    void update_shouldThrowException_whenNoRowsUpdated() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId("DR99");
        doctor.setFirstName("Test");
        doctor.setLastName("User");
        doctor.setEmail("test@example.com");
        doctor.setPassword("pass123");
        doctor.setSpecialization("Virology");

        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            RuntimeException ex = assertThrows(RuntimeException.class, () -> doctorRepository.update(doctor));
            assertTrue(ex.getMessage().contains("No doctor found with id"));
        }
    }
}
