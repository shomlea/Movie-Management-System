package com.example.movie_management_system.service;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.StaffAssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepository staffAssignmentRepository;
    private final SupportStaffService supportStaffService;
    private final TechnicalOperatorService technicalOperatorService;
    private final ScreeningService screeningService;

    public StaffAssignmentService(StaffAssignmentRepository staffAssignmentRepository,  SupportStaffService supportStaffService, TechnicalOperatorService technicalOperatorService, ScreeningService screeningService) {
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.supportStaffService = supportStaffService;
        this.technicalOperatorService = technicalOperatorService;
        this.screeningService = screeningService;
    }

    private Staff findStaffById(Long staffId) {
        Optional<Staff> foundSupportStaff = supportStaffService.findById(staffId);
        if (foundSupportStaff.isPresent()) {
            return foundSupportStaff.get();
        }

        Optional<Staff> foundTechnicalOperator = technicalOperatorService.findById(staffId);
        if (foundTechnicalOperator.isPresent()) {
            return foundTechnicalOperator.get();
        }

        throw new NoSuchElementException("Staff with ID " + staffId + " not found.");
    }

    @Transactional
    public StaffAssignment save(Long screeningId, Long staffId) {
        Screening screening = screeningService.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + screeningId + " not found."));

        Staff staff = findStaffById(staffId);

        StaffAssignment staffAssignment = new StaffAssignment(screening, staff);

        return staffAssignmentRepository.save(staffAssignment);
    }
    @Transactional
    public StaffAssignment update(Long id, Long screeningId, Long staffId){

        StaffAssignment staffAssignment = staffAssignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("StaffAssignment with ID " + id + " not found."));

        Screening screening = screeningService.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + screeningId + " not found."));

        Staff staff = findStaffById(staffId);

        staffAssignment.setScreening(screening);
        staffAssignment.setStaff(staff);

        return staffAssignmentRepository.save(staffAssignment);
    }

    @Transactional
    public void delete(Long id) {
        staffAssignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("StaffAssignment with ID " + id + " not found."));
        staffAssignmentRepository.deleteById(id);
    }

    public List<Staff> getAvailableStaff(){
        List<Staff> staff = new ArrayList<>(supportStaffService.findAll());
        staff.addAll(technicalOperatorService.findAll());
        return staff;
    }

    public List<Screening> getAvailableScreenings(){
        return screeningService.findAll();
    }



    public Optional<StaffAssignment> findById(Long id) {
        return staffAssignmentRepository.findById(id);
    }

    public List<StaffAssignment> findAll()
    {
        return staffAssignmentRepository.findAll();
    }
}
