package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.TechnicalOperatorRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
    public TechnicalOperator save(TechnicalOperator  technicalOperator) {
        Optional<TechnicalOperator> foundTechnicalOperator = technicalOperatorRepository.findByName(technicalOperator.getName());
        if(foundTechnicalOperator.isPresent())
            throw new DataIntegrityViolationException("There is already a Theatre with that name");
        return technicalOperatorRepository.save(technicalOperator);
    }

    @Transactional
    public TechnicalOperator update(Long id, TechnicalOperator updatedOperator) {
        TechnicalOperator existingOperator = technicalOperatorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Technical Operator with ID " + id + " not found."));

        Optional<TechnicalOperator> foundTechnicalOperator = technicalOperatorRepository.findByName(existingOperator.getName());
        if(foundTechnicalOperator.isPresent() && !foundTechnicalOperator.get().getId().equals(existingOperator.getId()))
            throw new DataIntegrityViolationException("There is already a Theatre with that name");

        existingOperator.setName(updatedOperator.getName());
        existingOperator.setSalary(updatedOperator.getSalary());
        existingOperator.setSpecialization(updatedOperator.getSpecialization());

        return technicalOperatorRepository.save(existingOperator);
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
