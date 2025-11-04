package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.repository.TechnicalOperatorRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TechnicalOperatorService {
    @Autowired
    TechnicalOperatorRepositoryInMemory technicalOperatorRepository;

    public void add(String name, int salary, Specialization specialization) {
        String id = UUID.randomUUID().toString();
        TechnicalOperator technicalOperator = new TechnicalOperator(id, name, salary, specialization);
        technicalOperatorRepository.add(technicalOperator);
    }
    public List<TechnicalOperator> getAll() {
        return technicalOperatorRepository.getAll();
    }
    public Optional<TechnicalOperator> findById(String id) {
        return technicalOperatorRepository.findById(id);
    }
    public void remove(String id) {
        technicalOperatorRepository.remove(id);
    }
}
