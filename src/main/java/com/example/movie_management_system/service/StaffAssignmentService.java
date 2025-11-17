package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Staff;
import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.repository.StaffAssignmentRepositoryInFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepositoryInFile staffAssignmentRepository;
    private final SupportStaffService supportStaffService;
    private final TechnicalOperatorService technicalOperatorService;
    private final ScreeningService screeningService;

    public void add(String screeningId, String staffId) {
        String id = UUID.randomUUID().toString();
        StaffAssignment staffAssignment = new StaffAssignment(id, screeningId, staffId);
        staffAssignmentRepository.add(staffAssignment);
    }

    public StaffAssignmentService(StaffAssignmentRepositoryInFile staffAssignmentRepository,  SupportStaffService supportStaffService, TechnicalOperatorService technicalOperatorService, ScreeningService screeningService) {
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.supportStaffService = supportStaffService;
        this.technicalOperatorService = technicalOperatorService;
        this.screeningService = screeningService;
    }

    public boolean update(String id, String screeningId, String staffId){
        StaffAssignment staffAssignment = new StaffAssignment(id, screeningId, staffId);
        return staffAssignmentRepository.update(id, staffAssignment);
    }

    public List<Staff> getAvailableStaff(){
        List<Staff> staff = new ArrayList<>(supportStaffService.getAll());
        staff.addAll(technicalOperatorService.getAll());
        return staff;
    }

    public List<Screening> getAvailableScreenings(){
        return screeningService.getAll();
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
