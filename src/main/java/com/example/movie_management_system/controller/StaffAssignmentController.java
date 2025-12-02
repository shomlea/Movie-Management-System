package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.service.StaffAssignmentService;
import com.example.movie_management_system.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService staffAssignmentService;
    private final ScreeningService screeningService; // NEW


    // Adjusted constructor injection to include new dependencies
    @Autowired
    public StaffAssignmentController(
            StaffAssignmentService staffAssignmentService,
            ScreeningService screeningService

    ) {
        this.staffAssignmentService = staffAssignmentService;
        this.screeningService = screeningService;

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
    @PostMapping
    public String addStaffAssignment(
            @ModelAttribute @Valid StaffAssignment staffAssignment,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableScreenings", screeningService.findAll());
            model.addAttribute("availableStaff", staffAssignmentService.getAvailableStaff());
            return "staffAssignment/form";
        }

        try {
            staffAssignmentService.save(staffAssignment);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
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

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateStaffAssignment(
            @PathVariable Long id,
            @ModelAttribute @Valid StaffAssignment staffAssignment,
            BindingResult result,
            Model model
    ) {
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