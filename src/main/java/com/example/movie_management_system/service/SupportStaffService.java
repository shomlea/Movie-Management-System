package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.repository.deprecated.SupportStaffRepositoryInFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportStaffService {
    @Autowired
    private SupportStaffRepositoryInFile supportStaffRepository;

    public SupportStaffService(SupportStaffRepositoryInFile supportStaffRepository) {
        this.supportStaffRepository = supportStaffRepository;
    }

    public void add(String name, double salary, Role role) {
        String id = UUID.randomUUID().toString();
        SupportStaff supportStaff = new SupportStaff(id, name, salary, role);
        supportStaffRepository.add(supportStaff);
    }

    public boolean update(String id, String name, double salary, Role role){
        SupportStaff supportStaff = new SupportStaff(id, name, salary, role);
        return supportStaffRepository.update(id, supportStaff);
    }

    public void remove(String id) {
        supportStaffRepository.remove(id);
    }
    public List<SupportStaff> getAll() {
        return supportStaffRepository.getAll();
    }
    public Optional<SupportStaff> findById(String id) {
        return supportStaffRepository.findById(id);
    }

}
