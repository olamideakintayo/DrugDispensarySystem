package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.data.models.Patient;
import com.boaDispensarySystem.dtos.requests.CreatePatientRequest;
import com.boaDispensarySystem.dtos.requests.UpdatePatientRequest;
import com.boaDispensarySystem.dtos.responses.CreatePatientResponse;


import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientMapper {
    public static Patient mapResultSetToPatient (ResultSet rSet) throws SQLException {
        Patient patient = new Patient();

        patient.setId(rSet.getString("id"));
        patient.setFirstName(rSet.getString("firstName"));
        patient.setLastName(rSet.getString("lastName"));
        patient.setAge(rSet.getString("age"));
        patient.setGender(rSet.getString("gender"));
        patient.setEmail(rSet.getString("email"));

        return patient;

    }

    public static Patient mapCreatePatientRequesToPatient (CreatePatientRequest request, String id){
        Patient patient = new Patient();

        patient.setId(id);
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setEmail(request.getEmail());
        return patient;


    }
    public  static CreatePatientResponse mapPatientToCreatePatientResponse (Patient patient){
        CreatePatientResponse response = new CreatePatientREsponse();

        response.setId(patient.getId());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setAge(patient.getAge());
        response.setGender(patient.getGender());
        response.setEmail(patient.getEmail());
        return response;
    }

    public  static Patient mapUpdatePatientResponseToPatient (Patient existingPatient, UpdatePatientRequest request){
        if(request.getFirstName != null) existingPatient.setFirstName(request.getFirstName());
        if(request.getLastName != null) existingPatient.setLastName(request.getLastName());
        if(request.getAge != null) existingPatient.setAge(request.getAge());
        if (request.getGender != null) existingPatient.setGender(request.getGender());
        if(request.setEmail != null) existingPatient.setEmail(request.getEmail());
        return existingPatient;


    }

}
