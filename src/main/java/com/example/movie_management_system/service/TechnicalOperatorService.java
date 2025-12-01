package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.repository.TechnicalOperatorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class TechnicalOperatorService {
    private final TechnicalOperatorRepository technicalOperatorRepository;

    public TechnicalOperatorService(TechnicalOperatorRepository technicalOperatorRepository) {
        this.technicalOperatorRepository = technicalOperatorRepository;
    }

    @Transactional
    public TechnicalOperator save(String name, double salary, Specialization specialization) {
        TechnicalOperator technicalOperator = new TechnicalOperator(name, salary, specialization);
        return technicalOperatorRepository.save(technicalOperator);
    }

    @Transactional
    public TechnicalOperator update(Long id, String name, double salary, Specialization specialization) {
        TechnicalOperator technicalOperator = technicalOperatorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Technical Operator with ID " + id + " not found."));

        technicalOperator.setName(name);
        technicalOperator.setSalary(salary);
        technicalOperator.setSpecialization(specialization);

        return technicalOperatorRepository.save(technicalOperator);
    }
    @Transactional
    public void delete(Long id) {
        TechnicalOperator technicalOperator = technicalOperatorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Technical Operator with ID " + id + " not found."));
        technicalOperatorRepository.deleteById(id);
    }

    public List<TechnicalOperator> findAll() {
        return technicalOperatorRepository.findAll();
    }
    public Optional<TechnicalOperator> findById(Long id) {
        return technicalOperatorRepository.findById(id);
    }

}
