package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.repository.TechnicalOperatorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TechnicalOperatorService {
    private final TechnicalOperatorRepository technicalOperatorRepository;

    public TechnicalOperatorService(TechnicalOperatorRepository technicalOperatorRepository) {
        this.technicalOperatorRepository = technicalOperatorRepository;
    }

    @Transactional
    public void save(String name, double salary, Specialization specialization) {
        String id = UUID.randomUUID().toString();
        TechnicalOperator technicalOperator = new TechnicalOperator(id, name, salary, specialization);
        technicalOperatorRepository.save(technicalOperator);
    }

    @Transactional
    public void update(String id, String name, double salary, Specialization specialization) {
        TechnicalOperator technicalOperator = new TechnicalOperator(id, name, salary, specialization);
        technicalOperatorRepository.save(technicalOperator);
    }
    @Transactional
    public void delete(String id) {
        technicalOperatorRepository.deleteById(id);
    }

    public List<TechnicalOperator> findAll() {
        return technicalOperatorRepository.findAll();
    }
    public Optional<TechnicalOperator> findById(String id) {
        return technicalOperatorRepository.findById(id);
    }

}
