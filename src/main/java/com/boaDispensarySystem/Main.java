package com.boaDispensarySystem;

import com.boaDispensarySystem.controllers.AdminController;
import com.boaDispensarySystem.controllers.DoctorController;
import com.boaDispensarySystem.data.repositories.AdminRepositoryImpl;
import com.boaDispensarySystem.data.repositories.DoctorRepositoryImpl;
import com.boaDispensarySystem.dtos.requests.CreateAdminRequest;
import com.boaDispensarySystem.dtos.requests.CreateDoctorRequest;
import com.boaDispensarySystem.services.AdminServiceImpl;
import com.boaDispensarySystem.services.DoctorServiceImpl;
import com.boaDispensarySystem.utils.Authenticator;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Wire dependencies
        AdminServiceImpl adminService = new AdminServiceImpl(new AdminRepositoryImpl());
        DoctorServiceImpl doctorService = new DoctorServiceImpl(new DoctorRepositoryImpl());
        AdminController adminController = new AdminController(adminService);
        DoctorController doctorController = new DoctorController(doctorService);
        Authenticator authenticator = new Authenticator(adminController, doctorController);

        while (true) {
            String[] options = {"Register Admin", "Register Doctor", "Login Admin", "Login Doctor", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option", "BOA Dispensary System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> {
                    String username = JOptionPane.showInputDialog("Enter admin username:");
                    String password = JOptionPane.showInputDialog("Enter admin password:");

                    CreateAdminRequest req = new CreateAdminRequest();
                    req.setUsername(username);
                    req.setPassword(password);

                    authenticator.registerAdmin(req);
                    JOptionPane.showMessageDialog(null, "Admin registered!");
                }
                case 1 -> {
                    String firstName = JOptionPane.showInputDialog("Enter doctor's first name:");
                    String lastName = JOptionPane.showInputDialog("Enter doctor's last name:");
                    String email = JOptionPane.showInputDialog("Enter doctor's email:");
                    String password = JOptionPane.showInputDialog("Enter doctor's password:");
                    String specialization = JOptionPane.showInputDialog("Enter doctor's specialization:");

                    CreateDoctorRequest req = new CreateDoctorRequest();
                    req.setFirstName(firstName);
                    req.setLastName(lastName);
                    req.setEmail(email);
                    req.setPassword(password);
                    req.setSpecialization(specialization);

                    authenticator.registerDoctor(req);
                    JOptionPane.showMessageDialog(null, "Doctor registered!");
                }

                case 2 -> {
                    String username = JOptionPane.showInputDialog("Enter username:");
                    String password = JOptionPane.showInputDialog("Enter password:");

                    boolean success = authenticator.loginAdmin(username, password);
                    JOptionPane.showMessageDialog(null, success ? "Welcome Admin!" : "Invalid admin login.");
                }

                case 3 -> {
                    String email = JOptionPane.showInputDialog("Enter email:");
                    String password = JOptionPane.showInputDialog("Enter password:");

                    boolean success = authenticator.loginDoctor(email, password);
                    JOptionPane.showMessageDialog(null, success ? "Welcome Doctor!" : "Invalid doctor login.");
                }

                case 4 -> System.exit(0);
            }
        }
    }

    private static void adminDashboard(AdminController adminController) {
        String[] options = {"View All Admins", "Delete Admin", "Logout"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Admin Dashboard", "Admin",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> JOptionPane.showMessageDialog(null, adminController.getAllAdmins().toString());
                case 1 -> {
                    String username = JOptionPane.showInputDialog("Enter username to delete:");
                    boolean success = adminController.deleteAdminByUsername(username);
                    JOptionPane.showMessageDialog(null, success ? "Deleted!" : "Not found.");
                }
                case 2 -> { return; }
            }
        }
    }

    private static void doctorDashboard(DoctorController doctorController) {
        String[] options = {"View All Doctors", "Count Doctors", "Logout"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Doctor Dashboard", "Doctor",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0 -> JOptionPane.showMessageDialog(null, doctorController.getAllDoctors().toString());
                case 1 -> JOptionPane.showMessageDialog(null, "Total Doctors: " + doctorController.countDoctors());
                case 2 -> { return; }
            }
        }
    }
}
