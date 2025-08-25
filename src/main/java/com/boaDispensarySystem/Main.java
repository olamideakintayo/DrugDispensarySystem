package com.boaDispensarySystem;

import com.boaDispensarySystem.controllers.AdminController;
import com.boaDispensarySystem.controllers.DoctorController;
import com.boaDispensarySystem.data.models.Specialization;
import com.boaDispensarySystem.data.repositories.AdminRepositoryImpl;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.UpdateAdminRequest;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.dtos.requests.UpdateDoctorRequest;
import com.boaDispensarySystem.dtos.responses.CreateAdminResponse;
import com.boaDispensarySystem.dtos.responses.CreateDoctorResponse;
import com.boaDispensarySystem.services.AdminService;
import com.boaDispensarySystem.services.DoctorService;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        // Initialize repositories and services
        DoctorRepositoryImpl doctorRepo = new DoctorRepositoryImpl();
        DoctorService doctorService = new DoctorService(doctorRepo);
        DoctorController doctorController = new DoctorController(doctorService);

        AdminRepositoryImpl adminRepo = new AdminRepositoryImpl();
        AdminService adminService = new AdminService(adminRepo);
        AdminController adminController = new AdminController(adminService);

        while (true) {
            String[] mainOptions = {"Doctor", "Admin", "Exit"};
            int mainChoice = JOptionPane.showOptionDialog(null, "Select Role", "Drug Dispensary System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, mainOptions, mainOptions[0]);

            if (mainChoice == 0) {
                doctorAuthMenu(doctorController);
            } else if (mainChoice == 1) {
                adminAuthMenu(adminController);
            } else {
                break; // Exit program
            }
        }
    }

    // ======================= DOCTOR AUTH =========================
    private static void doctorAuthMenu(DoctorController doctorController) {
        while (true) {
            String[] options = {"Register", "Login", "Back"};
            int choice = JOptionPane.showOptionDialog(null, "Doctor Menu", "Doctor",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                // Register
                CreateDoctorRequest request = new CreateDoctorRequest();
                request.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
                request.setLastName(JOptionPane.showInputDialog("Enter last name:"));
                request.setEmail(JOptionPane.showInputDialog("Enter email:"));
                request.setPassword(JOptionPane.showInputDialog("Enter password:"));

                String[] specializations = {"GENERAL", "CARDIOLOGY", "NEUROLOGY", "PEDIATRICS", "SURGERY"};
                String specialization = (String) JOptionPane.showInputDialog(null, "Select specialization:",
                        "Specialization", JOptionPane.QUESTION_MESSAGE, null, specializations, specializations[0]);
                request.setSpecialization(spe);

                try {
                    CreateDoctorResponse response = doctorController.createDoctor(request);
                    JOptionPane.showMessageDialog(null, "Doctor registered! ID: " + response.getId());
                    doctorMenu(doctorController, response.getId());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }

            } else if (choice == 1) {
                // Login
                String email = JOptionPane.showInputDialog("Enter email:");
                String password = JOptionPane.showInputDialog("Enter password:");

                try {
                    List<CreateDoctorResponse> doctors = doctorController.getAllDoctors();
                    CreateDoctorResponse loggedInDoctor = doctors.stream()
                            .filter(d -> d.getEmail().equals(email) && d.getPassword().equals(password))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

                    JOptionPane.showMessageDialog(null, "Login successful! Welcome " + loggedInDoctor.getFirstName());
                    doctorMenu(doctorController, loggedInDoctor.getId());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
                }

            } else {
                break; // Back to main menu
            }
        }
    }

    private static void doctorMenu(DoctorController doctorController, String doctorId) {
        while (true) {
            String[] options = {"View My Info", "Update My Info", "View All Doctors",
                    "Delete My Account", "Count Doctors", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Doctor Operations", "Doctor Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            try {
                switch (choice) {
                    case 0 -> { // View My Info
                        CreateDoctorResponse doctor = doctorController.getDoctorById(doctorId);
                        JOptionPane.showMessageDialog(null, """
                                ID: %s
                                Name: %s %s
                                Email: %s
                                Specialization: %s
                                """.formatted(doctor.getId(), doctor.getFirstName(), doctor.getLastName(),
                                doctor.getEmail(), doctor.getSpecialization()));
                    }
                    case 1 -> { // Update My Info
                        UpdateDoctorRequest update = new UpdateDoctorRequest();
                        update.setFirstName(JOptionPane.showInputDialog("New first name:"));
                        update.setLastName(JOptionPane.showInputDialog("New last name:"));
                        update.setPassword(JOptionPane.showInputDialog("New password (leave blank to keep):"));
                        update.setSpecialization(Specialization.valueOf(JOptionPane.showInputDialog("New specialization:")));

                        CreateDoctorResponse updatedDoctor = doctorController.updateDoctor(doctorId, update);
                        JOptionPane.showMessageDialog(null, "Updated successfully! ID: " + updatedDoctor.getId());
                    }
                    case 2 -> { // View All Doctors
                        List<CreateDoctorResponse> doctors = doctorController.getAllDoctors();
                        StringBuilder sb = new StringBuilder();
                        for (CreateDoctorResponse d : doctors) {
                            sb.append("""
                                    ID: %s
                                    Name: %s %s
                                    Email: %s
                                    Specialization: %s

                                    """.formatted(d.getId(), d.getFirstName(), d.getLastName(), d.getEmail(), d.getSpecialization()));
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }
                    case 3 -> { // Delete My Account
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?");
                        if (confirm == JOptionPane.YES_OPTION) {
                            boolean deleted = doctorController.deleteDoctor(doctorId);
                            JOptionPane.showMessageDialog(null, deleted ? "Deleted successfully" : "Delete failed");
                            return;
                        }
                    }
                    case 4 -> { // Count Doctors
                        long count = doctorController.countDoctors();
                        JOptionPane.showMessageDialog(null, "Total doctors: " + count);
                    }
                    default -> { // Logout
                        return;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    // ======================= ADMIN AUTH =========================
    private static void adminAuthMenu(AdminController adminController) {
        while (true) {
            String[] options = {"Register", "Login", "Back"};
            int choice = JOptionPane.showOptionDialog(null, "Admin Menu", "Admin",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                CreateAdminRequest request = new CreateAdminRequest();
                request.setUsername(JOptionPane.showInputDialog("Enter username:"));
                request.setPassword(JOptionPane.showInputDialog("Enter password:"));

                try {
                    CreateAdminResponse response = adminController.createAdmin(request);
                    JOptionPane.showMessageDialog(null, "Admin registered! Username: " + response.getUsername());
                    adminMenu(adminController, response.getUsername());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            } else if (choice == 1) {
                String username = JOptionPane.showInputDialog("Enter username:");
                String password = JOptionPane.showInputDialog("Enter password:");

                try {
                    Optional<CreateAdminResponse> adminOpt = adminController.getAdminByUsername(username);
                    CreateAdminResponse admin = adminOpt.orElseThrow(() -> new RuntimeException("Invalid credentials"));
                    if (!admin.getPassword().equals(password)) throw new RuntimeException("Invalid credentials");

                    JOptionPane.showMessageDialog(null, "Login successful! Welcome " + admin.getUsername());
                    adminMenu(adminController, admin.getUsername());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
                }

            } else {
                break; // Back to main menu
            }
        }
    }

    private static void adminMenu(AdminController adminController, String username) {
        while (true) {
            String[] options = {"View My Info", "Update My Info", "View All Admins", "Delete My Account", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Admin Operations", "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            try {
                switch (choice) {
                    case 0 -> { // View My Info
                        CreateAdminResponse admin = adminController.getAdminByUsername(username).orElseThrow();
                        JOptionPane.showMessageDialog(null, """
                                Username: %s
                                Password: %s
                                """.formatted(admin.getUsername(), admin.getPassword()));
                    }
                    case 1 -> { // Update My Info
                        UpdateAdminRequest update = new UpdateAdminRequest();
                        update.setUsername(username);
                        update.setPassword(JOptionPane.showInputDialog("New password:"));

                        CreateAdminResponse updatedAdmin = adminController.updateAdmin(update);
                        JOptionPane.showMessageDialog(null, "Updated successfully! Username: " + updatedAdmin.getUsername());
                    }
                    case 2 -> { // View All Admins
                        List<CreateAdminResponse> admins = adminController.getAllAdmins();
                        StringBuilder sb = new StringBuilder();
                        for (CreateAdminResponse a : admins) {
                            sb.append("""
                                    Username: %s
                                    Password: %s

                                    """.formatted(a.getUsername(), a.getPassword()));
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }
                    case 3 -> { // Delete My Account
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?");
                        if (confirm == JOptionPane.YES_OPTION) {
                            boolean deleted = adminController.deleteAdminByUsername(username);
                            JOptionPane.showMessageDialog(null, deleted ? "Deleted successfully" : "Delete failed");
                            return;
                        }
                    }
                    default -> { // Logout
                        return;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }
}
