package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Drug;

public interface DrugRepository {
    long count();

    Drug save(Drug drug);

    Drug deleteByID(int id);

    Drug findById(int id);

    Drug findByName(String name);
}
