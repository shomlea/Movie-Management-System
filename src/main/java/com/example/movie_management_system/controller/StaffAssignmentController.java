package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.service.StaffAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService staffAssignmentService;

    @Autowired
    public StaffAssignmentController(StaffAssignmentService staffAssignmentService) {
        this.staffAssignmentService = staffAssignmentService;
    }

    // List all staff assignments
    @GetMapping
    public String listAll(Model model) {
        List<StaffAssignment> assignments = staffAssignmentService.findAllStaffAssignments();
        model.addAttribute("staffAssignments", assignments);
        return "staffAssignment/index"; // template: templates/staffAssignment/index.html
    }

    // Show form to create new staff assignment
    @GetMapping("/add")
    public String showAddForm() {
        return "staffAssignment/form"; // template: templates/staffAssignment/form.html
    }

    // Create new staff assignment
    @PostMapping("/add")
    public String addStaffAssignment(
            @RequestParam("screeningId") String screeningId,
            @RequestParam("staffId") String staffId
    ) {
        staffAssignmentService.createStaffAssignment(screeningId, staffId);
        return "redirect:/staff-assignments";
    }

    // Delete assignment by ID
    @GetMapping("/delete/{id}")
    public String deleteStaffAssignment(@PathVariable String id) {
        staffAssignmentService.removeStaffAssignment(id);
        return "redirect:/staff-assignments";
    }
}
