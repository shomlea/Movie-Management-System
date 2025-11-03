package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.repository.SupportStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.processing.RoundEnvironment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportStaffService {
    @Autowired
    private SupportStaffRepository supportStaffRepository;

    public SupportStaff addSupportStaff(String name, int salary, Role role) {
        String id = UUID.randomUUID().toString();
        SupportStaff supportStaff = new SupportStaff(id, name, salary, role);
        return supportStaffRepository.save(supportStaff);
    }
    public void removeSupportStaff(String id) {
        supportStaffRepository.deleteById(id);
    }
    public List<SupportStaff> getAllSupportStaff(String id) {
        return supportStaffRepository.findAll();
    }
    public Optional<SupportStaff> getById(String id) {
        return supportStaffRepository.findById(id);
    }

}
