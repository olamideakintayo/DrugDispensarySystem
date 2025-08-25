package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.DbConnection;
import com.boaDispensarySystem.data.models.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminRepositoryImplTest {

    private AdminRepositoryImpl adminRepository;

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        adminRepository = new AdminRepositoryImpl();

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void save_shouldReturnAdmin() throws Exception {
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("pass123");

        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            Admin result = adminRepository.save(admin);

            assertNotNull(result);
            assertEquals("admin1", result.getUsername());
            verify(mockPreparedStatement).setString(1, "admin1");
            verify(mockPreparedStatement).setString(2, "pass123");
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void findByUsername_shouldReturnAdmin_whenExists() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("username")).thenReturn("admin1");
            when(mockResultSet.getString("password")).thenReturn("pass123");

            Optional<Admin> result = adminRepository.findByUsername("admin1");

            assertTrue(result.isPresent());
            assertEquals("admin1", result.get().getUsername());
            assertEquals("pass123", result.get().getPassword());
        }
    }

    @Test
    void findByUsername_shouldReturnEmpty_whenNotExists() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            Optional<Admin> result = adminRepository.findByUsername("unknown");

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void findAll_shouldReturnList() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, true, false); // two admins
            when(mockResultSet.getString("username")).thenReturn("admin1", "admin2");
            when(mockResultSet.getString("password")).thenReturn("pass1", "pass2");

            List<Admin> admins = adminRepository.findAll();

            assertEquals(2, admins.size());
            assertEquals("admin1", admins.get(0).getUsername());
            assertEquals("admin2", admins.get(1).getUsername());
        }
    }

    @Test
    void deleteByUsername_shouldReturnTrue_whenDeleted() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean deleted = adminRepository.deleteByUsername("admin1");

            assertTrue(deleted);
            verify(mockPreparedStatement).setString(1, "admin1");
        }
    }

    @Test
    void deleteByUsername_shouldReturnFalse_whenNotDeleted() throws Exception {
        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean deleted = adminRepository.deleteByUsername("unknown");

            assertFalse(deleted);
        }
    }

    @Test
    void update_shouldReturnAdmin_whenUpdated() throws Exception {
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("newPass");

        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            Admin result = adminRepository.update(admin);

            assertEquals("admin1", result.getUsername());
            assertEquals("newPass", result.getPassword());
            verify(mockPreparedStatement).setString(1, "newPass");
            verify(mockPreparedStatement).setString(2, "admin1");
        }
    }

    @Test
    void update_shouldThrowException_whenNoRowsUpdated() throws Exception {
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setPassword("newPass");

        try (MockedStatic<DbConnection> dbMock = mockStatic(DbConnection.class)) {
            dbMock.when(DbConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            RuntimeException ex = assertThrows(RuntimeException.class, () -> adminRepository.update(admin));
            assertTrue(ex.getMessage().contains("No admin found with username"));
        }
    }
}
