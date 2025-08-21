package com.boaDispensarySystem.dtos.requests;

import com.boaDispensarySystem.data.models.Specialization;

public class CreateDoctorRequest {
    private String firstName;
    private String lastName;
    private Specialization specialization;
    private String email;
    private String password;




    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Specialization getSpecialization() { return specialization; }
    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
