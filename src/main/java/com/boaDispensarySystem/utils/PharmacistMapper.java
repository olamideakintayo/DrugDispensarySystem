/* package com.boaDispensarySystem.utils;


import com.boaDispensarySystem.data.models.Pharmacist;
import com.boaDispensarySystem.dtos.requests.CreatePharmacistRequest;
import com.boaDispensarySystem.dtos.responses.CreatePharmacistResponse;
import com.boaDispensarySystem.dtos.requests.UpdatePharmacistRequest;


import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacistMapper {

    public static Pharmacist mapResultSetToPharmacist (ResultSet rset) throws SQLException {
        Pharmacist pharmacist = new Pharmacist();

        pharmacist.setId(rset.getString("id"));
        pharmacist.setFirstName(rset.getString("firstName"));
        pharmacist.setLastName(rset.getString("lastName"));
        pharmacist.setEmail(rset.getString("email"));
        pharmacist.setPassword(rset.getString("password"));

        return pharmacist;


    }

    public static Pharmacist mapCreateParmacistRequestToPharmacist (CreatePharmacistRequest request, String id){
        Pharmacist pharmacist = new Pharmacist();

        pharmacist.setId(request.getString());
        pharmacist.setFirstName(request.getString());
        pharmacist.setLastName(request.getString());
        pharmacist.setEmail(request.getString());
        pharmacist.setPassword(request.getString());

        return pharmacist;



    }

    public static CreatePharmacistResponse MapPharmacistToPharmacistResponse (Pharmacist pharmacist){
        CreatePharmacistResponse response = new CreatePharmacistResponse();

        response.setId(pharmacist.getString());
        response.setFirstName(pharmacist.getString());
        response.setLastName(pharmacist.getString());
        response.setEmail(pharmacist.getString());
        response.setPassword(pharmacist.getString());

        return response;
    }

    public static Pharmacist mapUpdatePharmacistRequestToPharmacist (Pharmacist existingPharmacist, UpdatePharmacistRequest request){
        if (request.getFirstName() != null ) existingPharmacist.setFirstName(request.getString());
        if (request.getLastName() != null) existingPharmacist.setLastName(request.getString());
        if (request.getEmail() != null) existingPharmacist.setEmail(request.getString());
        if (request.getPassword() != null)existingPharmacist.setPassword(request.getString());

        return existingPharmacist;
    }


}
*/