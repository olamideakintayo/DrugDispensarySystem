package com.boaDispensarySystem.utils;

import com.boaDispensarySystem.data.models.Category;
import com.boaDispensarySystem.data.models.Drug;
import com.boaDispensarySystem.data.models.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DrugMapper {
    public static Drug mapResultSetToDrug (ResultSet rtSet) throws SQLException {
        Drug drug = new Drug();

        drug.setId(rtSet.getInt("id"));
        drug.setName(rtSet.getString("name"));
        drug.setType(Type.valueOf(rtSet.getString("type")));
        drug.setCategory(Category.valueOf(rtSet.getString("category")));
        drug.setExpiryDate(LocalDate.parse(rtSet.getString("expiryDate")));
        drug.setManufactureDate(LocalDate.parse(rtSet.getString("manufactureDate")));
        drug.setDateAdded((LocalDate.now()));


    }
}
