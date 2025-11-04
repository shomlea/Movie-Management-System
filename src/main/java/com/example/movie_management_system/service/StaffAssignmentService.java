package com.example.movie_management_system.service;

import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.repository.StaffAssignmentRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepositoryInMemory staffAssignmentRepository;

    public StaffAssignmentService(StaffAssignmentRepositoryInMemory staffAssignmentRepository) {
        this.staffAssignmentRepository = staffAssignmentRepository;
    }

    public void add(String screeningId, String staffId) {
        String id = UUID.randomUUID().toString();
        StaffAssignment staffAssignment = new StaffAssignment(id, screeningId, staffId);
        staffAssignmentRepository.add(staffAssignment);
    }

    public void remove(String id) {
        staffAssignmentRepository.remove(id);
    }

    public Optional<StaffAssignment> findById(String id) {
        return staffAssignmentRepository.findById(id);
    }

    public List<StaffAssignment> getAll()
    {
        return staffAssignmentRepository.getAll();
    }
}
