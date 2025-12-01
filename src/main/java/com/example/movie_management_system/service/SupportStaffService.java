package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.repository.SupportStaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SupportStaff save(String name, double salary, Role role) {
        SupportStaff supportStaff = new SupportStaff(name, salary, role);
        return supportStaffRepository.save(supportStaff);
    }
    @Transactional
    public SupportStaff update(Long id, String name, double salary, Role role){
        SupportStaff supportStaff = supportStaffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("SupportStaff with ID " + id + " not found."));

        supportStaff.setName(name);
        supportStaff.setSalary(salary);
        supportStaff.setRole(role);

        return supportStaffRepository.save(supportStaff);
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
