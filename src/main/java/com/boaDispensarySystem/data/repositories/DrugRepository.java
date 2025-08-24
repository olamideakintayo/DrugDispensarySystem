package com.boaDispensarySystem.data.repositories;

import com.boaDispensarySystem.data.models.Drug;

import java.util.Optional;

public interface DrugRepository {
    long count();

    Drug save(Drug drug);

    boolean deleteByID(int id);

    Optional<Drug> findById(int id);

    Optional<Drug> findByName(String name);
}
