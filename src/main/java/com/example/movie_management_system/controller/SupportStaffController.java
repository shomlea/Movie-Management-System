package com.example.movie_management_system.controller;

import com.example.movie_management_system.dto.SupportStaffFilterDto;
import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.service.SupportStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;
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
    public String listAll(SupportStaffFilterDto filter, Pageable pageable, Model model) {
        Page<SupportStaff> staffPage = supportStaffService.findAll(filter, pageable);
        model.addAttribute("allRoles", Role.values());
        model.addAttribute("staffPage", staffPage);
        model.addAttribute("filter", filter);
        return "supportStaff/index";
    }

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("supportStaff", new SupportStaff());
        model.addAttribute("roles", Role.values());
        return "supportStaff/form";
    }

    // --- CREATE (Process Form) ---
    @PostMapping
    public String addSupportStaff(
            @ModelAttribute @Valid SupportStaff supportStaff,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "supportStaff/form";
        }

        try {
            supportStaffService.save(supportStaff);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("roles", Role.values());
            return "supportStaff/form";
        }

        return "redirect:/support-staff";
    }

    @PostMapping("/remove/{id}")
    public String removeSupportStaff(@PathVariable Long id) {
        supportStaffService.delete(id);
        return "redirect:/support-staff";
    }

    @GetMapping("view/{id}")
    public String viewSupportStaff(@PathVariable Long id, Model model) {
        Optional<SupportStaff> supportStaff = supportStaffService.findById(id);
        if (supportStaff.isPresent()) {
            model.addAttribute("supportStaff", supportStaff.get());
            return "supportStaff/view";
        } else {
            return "redirect:/support-staff";
        }
    }

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return supportStaffService.findById(id)
                .map(staff -> {
                    model.addAttribute("supportStaff", staff);
                    model.addAttribute("roles", Role.values());
                    return "supportStaff/update";
                })
                .orElse("redirect:/support-staff");
    }

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateSupportStaff(
            @PathVariable Long id,
            @ModelAttribute @Valid SupportStaff supportStaff,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "supportStaff/update";
        }

        try {
            supportStaffService.update(id, supportStaff);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("roles", Role.values());
            return "supportStaff/update";
        }

        return "redirect:/support-staff";
    }
}