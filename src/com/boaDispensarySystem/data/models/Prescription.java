package com.boaDispensarySystem.data.models;

import java.time.LocalDateTime;
import java.util.List;

public class Prescription {
    private String prescriptionCode;
    private String patientId;
    private String doctorId;
    private String pharmacistId;
    private List<Drug> drugs;
    private PrescriptionStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateDispensed;


    public String getPrescriptionCode() {
        return prescriptionCode;
    }
    public void setPrescriptionCode(String prescriptionCode) {
        this.prescriptionCode = prescriptionCode;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId){
        this.pharmacistId = pharmacistId;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }
    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateDispensed() {
        return dateDispensed;
    }

    public void setDateDispensed(LocalDateTime dateDispensed) {
        this.dateDispensed = dateDispensed;
    }
}
