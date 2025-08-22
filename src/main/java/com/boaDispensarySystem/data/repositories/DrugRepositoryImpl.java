package com.boaDispensarySystem.data.repositories;



import com.boaDispensarySystem.data.models.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugRepositoryImpl implements DrugRepository {

    private static List<Drug> drugs = new ArrayList<>();

    public long count() {
        return drugs.size();
    }

    public Drug save(Drug drug) {
        if(isNew(drug)) saveNew(drug);
        else update(drug);
        return drug;
    }

    private void update(Drug drug) {
        deleteByID(drug.getId());
        drugs.add(drug);
    }

    public Drug deleteByID(int id) {
        for(int index = 0; index < drugs.size(); index++) {
            if(drugs.get(index).getId() == id) drugs.remove(index);
        }
        return null;
    }

    private void saveNew(Drug drug) {
        drug.setId(generateId());
        drugs.add(drug);
    }

    private int generateId() {
        return drugs.size() +1;
    }

    private boolean isNew(Drug drug) {
        return drug.getId() == 0;
    }


    public Drug findById(int id) {
        for (Drug drug  :  drugs) {
            if (drug.getId() == id) return drug;
        }
        return null;
    }

    public Drug findByName(String name) {
        for (Drug drug :  drugs) {
            if (drug.getName().equals(name)) return drug;
        }
        return null;
    }

    public List<Drug> findAll() {
        return drugs;
    }



}

