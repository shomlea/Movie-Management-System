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

    @GetMapping
    public String listAll(Model model) {
        List<StaffAssignment> assignments = staffAssignmentService.getAll();
        model.addAttribute("staffAssignments", assignments);
        return "staffAssignment/index"; // template: templates/staffAssignment/index.html
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "staffAssignment/form"; // template: templates/staffAssignment/form.html
    }


    @PostMapping("/add")
    public String addStaffAssignment(
            @RequestParam("screeningId") String screeningId,
            @RequestParam("staffId") String staffId
    ) {
        staffAssignmentService.save(screeningId, staffId);
        return "redirect:/staff-assignments";
    }

    @PostMapping("/remove/{id}")
    public String removeStaffAssignment(@PathVariable String id) {
        staffAssignmentService.remove(id);
        return "redirect:/staff-assignments";
    }
}
