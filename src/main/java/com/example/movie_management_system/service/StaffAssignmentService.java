package com.example.movie_management_system.service;

import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.repository.StaffAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepository staffAssignmentRepository;

    public StaffAssignmentService(StaffAssignmentRepository staffAssignmentRepository) {
        this.staffAssignmentRepository = staffAssignmentRepository;
    }

    public StaffAssignment createStaffAssignment(String screeningId, String staffId) {
        String id = UUID.randomUUID().toString();
        StaffAssignment staffAssignment = new StaffAssignment(id, screeningId, staffId);
        return staffAssignmentRepository.save(staffAssignment);
    }

    public void removeStaffAssignment(String id) {
        staffAssignmentRepository.deleteById(id);
    }

    public Optional<StaffAssignment> findById(String id) {
        return staffAssignmentRepository.findById(id);
    }

    public List<StaffAssignment> findAllStaffAssignments()
    {
        return staffAssignmentRepository.findAll();
    }
}
