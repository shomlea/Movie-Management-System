package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Staff;
import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.service.StaffAssignmentService;
import com.example.movie_management_system.service.ScreeningService;
import com.example.movie_management_system.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService staffAssignmentService;
    private final ScreeningService screeningService; // NEW
    private final StaffService staffService;


    // Adjusted constructor injection to include new dependencies
    @Autowired
    public StaffAssignmentController(
            StaffAssignmentService staffAssignmentService,
            ScreeningService screeningService,
            StaffService staffService

    ) {
        this.staffAssignmentService = staffAssignmentService;
        this.screeningService = screeningService;
        this.staffService = staffService;

    }

    @GetMapping
    public String listAll(Model model) {
        List<StaffAssignment> assignments = staffAssignmentService.findAll();
        model.addAttribute("staffAssignments", assignments);
        return "staffAssignment/index";
    }

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model) {
        SupportStaff placeholderStaff = new SupportStaff();

        StaffAssignment assignment = new StaffAssignment();

        assignment.setStaff(placeholderStaff);

        model.addAttribute("staffAssignment", assignment);
        model.addAttribute("availableScreenings", screeningService.findAll());
        model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff()); // Uses service helper
        return "staffAssignment/form";
    }


    // --- CREATE (Process Form) ---
//    @PostMapping
//    public String addStaffAssignment(
//            @ModelAttribute @Valid StaffAssignment staffAssignment,
//            BindingResult result,
//            Model model
//    ) {
//        if (result.hasErrors()) {
//            model.addAttribute("availableScreenings", screeningService.findAll());
//            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
//            return "staffAssignment/form";
//        }
//
//        try {
//            staffAssignmentService.save(staffAssignment);
//        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
//            model.addAttribute("errorMessage", "Error: " + e.getMessage());
//            model.addAttribute("availableScreenings", screeningService.findAll());
//            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
//            return "staffAssignment/form";
//        }
//        return "redirect:/staff-assignments";
//    }

    @PostMapping
    public String addStaffAssignment(

            @ModelAttribute StaffAssignment staffAssignment,

            @RequestParam("staffId") Long staffId,
            @RequestParam("screeningId") Long screeningId,

            BindingResult result,
            Model model
    ) {


        try {

            Staff staff = staffService.resolveStaffById(staffId); // <--- Implement this helper method
            if (staff == null) {
                result.rejectValue("staff", "staff.notFound", "The selected staff member was not found.");
            } else {
                staffAssignment.setStaff(staff);
            }

            Screening screening = screeningService.findById(screeningId)
                    .orElse(null);
            if (screening == null) {
                result.rejectValue("screening", "screening.notFound", "The selected screening was not found.");
            } else {
                staffAssignment.setScreening(screening);
            }

        } catch (NumberFormatException e) {
            result.reject("global.error", "Invalid ID submitted.");
        }


        if (result.hasErrors()) {
            // Reload all necessary model attributes
            model.addAttribute("availableScreenings", screeningService.findAll());
            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
            return "staffAssignment/form";
        }


        try {
            staffAssignmentService.save(staffAssignment);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            // Handle service-level exceptions
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableScreenings", screeningService.findAll());
            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
            return "staffAssignment/form";
        }

        return "redirect:/staff-assignments";
    }


    @PostMapping("/remove/{id}")
    public String removeStaffAssignment(@PathVariable Long id) {
        staffAssignmentService.delete(id);
        return "redirect:/staff-assignments";
    }

    @GetMapping("view/{id}")
    public String viewStaffAssignment(@PathVariable Long id, Model model) {
        return staffAssignmentService.findById(id)
                .map(assignment -> {
                    model.addAttribute("staffAssignment", assignment);
                    return "staffAssignment/view";
                })
                .orElse("redirect:/staff-assignments");
    }

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return staffAssignmentService.findById(id)
                .map(staffAssignment -> {
                    model.addAttribute("staffAssignment", staffAssignment);
                    model.addAttribute("availableScreenings", screeningService.findAll());
                    model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
                    return "staffAssignment/update";
                })
                .orElse("redirect:/staff-assignments");
    }

    @PostMapping("/update/{id}")
    public String updateStaffAssignment(
            @PathVariable Long id,
            @ModelAttribute @Valid StaffAssignment staffAssignment,
            @RequestParam("staffId") Long staffId,
            @RequestParam("screeningId") Long screeningId,
            BindingResult result,
            Model model
    ) {

        Staff staff = staffService.resolveStaffById(staffId);
        if (staff == null) {
            result.rejectValue("staff", "staff.notFound", "The selected staff member was not found.");
        }
        staffAssignment.setStaff(staff);

        Screening screening = screeningService.findById(screeningId).orElse(null);
        if (screening == null) {
            result.rejectValue("screening", "screening.notFound", "The selected screening was not found.");
        }
        staffAssignment.setScreening(screening);

        staffAssignment.setId(id);


        if (result.hasErrors()) {
            model.addAttribute("availableScreenings", screeningService.findAll());
            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
            return "staffAssignment/update";
        }

        try {

            staffAssignmentService.update(id, staffAssignment);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableScreenings", screeningService.findAll());
            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
            return "staffAssignment/update";
        }
        return "redirect:/staff-assignments";
    }
}