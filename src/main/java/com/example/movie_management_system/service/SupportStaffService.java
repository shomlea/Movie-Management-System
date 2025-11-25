package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.repository.SupportStaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportStaffService {
    private SupportStaffRepository supportStaffRepository;

    public SupportStaffService(SupportStaffRepository supportStaffRepository) {
        this.supportStaffRepository = supportStaffRepository;
    }
    @Transactional
    public void save(String name, double salary, Role role) {
        String id = UUID.randomUUID().toString();
        SupportStaff supportStaff = new SupportStaff(id, name, salary, role);
        supportStaffRepository.save(supportStaff);
    }
    @Transactional
    public void update(String id, String name, double salary, Role role){
        SupportStaff supportStaff = new SupportStaff(id, name, salary, role);
        supportStaffRepository.save(supportStaff);
    }
    @Transactional
    public void delete(String id) {
        supportStaffRepository.deleteById(id);
    }
    public List<SupportStaff> findAll() {
        return supportStaffRepository.findAll();
    }
    public Optional<SupportStaff> findById(String id) {
        return supportStaffRepository.findById(id);
    }

}
