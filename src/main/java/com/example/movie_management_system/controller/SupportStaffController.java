package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.service.SupportStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/support-staff")
public class SupportStaffController {

    private final SupportStaffService supportStaffService;

    @Autowired
    public SupportStaffController(SupportStaffService supportStaffService) {
        this.supportStaffService = supportStaffService;
    }

    // Display all support staff
    @GetMapping
    public String listAll(Model model) {
        List<SupportStaff> staffList = supportStaffService.getAllSupportStaff(null);
        model.addAttribute("supportStaff", staffList);
        return "supportStaff/index"; // Thymeleaf view: src/main/resources/templates/supportStaff/index.html
    }

    // Show form to add new staff
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("roles", Role.values());
        return "supportStaff/add";
    }

    // Add new staff (no getSalary() used)
    @PostMapping("/add")
    public String addSupportStaff(
            @RequestParam("name") String name,
            @RequestParam("salary") int salary,
            @RequestParam("role") Role role
    ) {
        supportStaffService.addSupportStaff(name, salary, role);
        return "redirect:/support-staff";
    }

    // Delete staff by ID
    @GetMapping("/delete/{id}")
    public String deleteSupportStaff(@PathVariable String id) {
        supportStaffService.removeSupportStaff(id);
        return "redirect:/support-staff";
    }

    // View single staff member
    @GetMapping("/{id}")
    public String viewSupportStaff(@PathVariable String id, Model model) {
        Optional<SupportStaff> supportStaff = supportStaffService.getById(id);
        if (supportStaff.isPresent()) {
            model.addAttribute("supportStaff", supportStaff.get());
            return "supportStaff/view";
        } else {
            return "redirect:/support-staff";
        }
    }
}
