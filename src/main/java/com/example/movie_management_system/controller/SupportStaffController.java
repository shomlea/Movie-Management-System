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

    @GetMapping
    public String listAll(Model model) {
        List<SupportStaff> staffList = supportStaffService.getAll();
        model.addAttribute("supportStaff", staffList);
        return "supportStaff/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("roles", Role.values());
        return "supportStaff/form"; // <-- changed from add to form
    }

    @PostMapping("/add")
    public String addSupportStaff(
            @RequestParam("name") String name,
            @RequestParam("salary") double salary,
            @RequestParam("role") Role role
    ) {
        supportStaffService.add(name, salary, role);
        return "redirect:/support-staff";
    }

    @PostMapping("/remove/{id}")
    public String removeSupportStaff(@PathVariable String id) {
        supportStaffService.remove(id);
        return "redirect:/support-staff";
    }

    @GetMapping("view/{id}")
    public String viewSupportStaff(@PathVariable String id, Model model) {
        Optional<SupportStaff> supportStaff = supportStaffService.findById(id);
        if (supportStaff.isPresent()) {
            model.addAttribute("supportStaff", supportStaff.get());
            return "supportStaff/view";
        } else {
            return "redirect:/support-staff";
        }
    }
}
