package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.repository.SupportStaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportStaffService {
    private SupportStaffRepository supportStaffRepository;

    public SupportStaffService(SupportStaffRepository supportStaffRepository) {
        this.supportStaffRepository = supportStaffRepository;
    }
    @Transactional
    public SupportStaff save(SupportStaff supportStaff) {
        Optional<SupportStaff> foundSupportStaff = supportStaffRepository.findByName(supportStaff.getName());
        if(foundSupportStaff.isPresent())
            throw new DataIntegrityViolationException("There is already a Theatre with that name");
        return supportStaffRepository.save(supportStaff);
    }
    @Transactional
    public SupportStaff update(Long id, SupportStaff updatedStaff) {
        SupportStaff existingStaff = supportStaffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("SupportStaff with ID " + id + " not found."));

        Optional<SupportStaff> foundSupportStaff = supportStaffRepository.findByName(existingStaff.getName());
        if(foundSupportStaff.isPresent() && !foundSupportStaff.get().getId().equals(existingStaff.getId()))
            throw new DataIntegrityViolationException("There is already a Theatre with that name");

        existingStaff.setName(updatedStaff.getName());
        existingStaff.setSalary(updatedStaff.getSalary());
        existingStaff.setRole(updatedStaff.getRole());

        return supportStaffRepository.save(existingStaff);
    }
    @Transactional
    public void delete(Long id) {
        if (!supportStaffRepository.existsById(id)) {
            throw new NoSuchElementException("SupportStaff with ID " + id + " not found.");
        }
        supportStaffRepository.deleteById(id);
    }
    public List<SupportStaff> findAll() {
        return supportStaffRepository.findAll();
    }
    public Optional<SupportStaff> findById(Long id) {
        return supportStaffRepository.findById(id);
    }

}
