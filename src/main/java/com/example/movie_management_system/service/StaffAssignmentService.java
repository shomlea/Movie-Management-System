package com.example.movie_management_system.service;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.deprecated.StaffAssignmentRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepositoryInFile staffAssignmentRepository;
    private final SupportStaffService supportStaffService;
    private final TechnicalOperatorService technicalOperatorService;
    private final ScreeningService screeningService;



    public StaffAssignmentService(StaffAssignmentRepositoryInFile staffAssignmentRepository,  SupportStaffService supportStaffService, TechnicalOperatorService technicalOperatorService, ScreeningService screeningService) {
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.supportStaffService = supportStaffService;
        this.technicalOperatorService = technicalOperatorService;
        this.screeningService = screeningService;
    }
    @Transactional
    public void add(String screeningId, String staffId) {
        String id = UUID.randomUUID().toString();
        StaffAssignment staffAssignment = new StaffAssignment(id, screeningId, staffId);
        if(screeningService.findById(screeningId).isPresent() && (supportStaffService.findById(staffId).isPresent() ||  technicalOperatorService.findById(staffId).isPresent())) {
            staffAssignmentRepository.add(staffAssignment);
            screeningService.addStaffAssignment(screeningId, staffAssignment);
        }
    }
    @Transactional
    public boolean update(String id, String screeningId, String staffId){
        StaffAssignment staffAssignment = new StaffAssignment(id, screeningId, staffId);
        screeningService.removeStaffAssignment(screeningId, id);
        staffAssignmentRepository.update(id, staffAssignment);
        return true;
    }

    @Transactional
    public boolean remove(String id) {
        Optional<StaffAssignment> staffAssignment = staffAssignmentRepository.findById(id);
        if (!staffAssignment.isPresent()) {
            throw new NoSuchElementException("StaffAssignment with id " + id + " not found");
        }
        screeningService.removeStaffAssignment(staffAssignment.get().getScreeningId(), id);
        staffAssignmentRepository.remove(id);

        return true;
    }

    public List<Staff> getAvailableStaff(){
        List<Staff> staff = new ArrayList<>(supportStaffService.getAll());
        staff.addAll(technicalOperatorService.getAll());
        return staff;
    }

    public List<Screening> getAvailableScreenings(){
        return screeningService.getAll();
    }



    public Optional<StaffAssignment> findById(String id) {
        return staffAssignmentRepository.findById(id);
    }

    public List<StaffAssignment> getAll()
    {
        return staffAssignmentRepository.getAll();
    }
}
