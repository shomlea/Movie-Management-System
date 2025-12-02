package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.service.TechnicalOperatorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/technical-operators")
public class TechnicalOperatorController {

    private final TechnicalOperatorService technicalOperatorService;

    public TechnicalOperatorController(TechnicalOperatorService technicalOperatorService) {
        this.technicalOperatorService = technicalOperatorService;
    }

    @GetMapping
    public String listAll(Model model) {
        List<TechnicalOperator> operators = technicalOperatorService.findAll();
        model.addAttribute("technicalOperators", operators);
        return "technicalOperator/index";
    }

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("technicalOperator", new TechnicalOperator());
        model.addAttribute("specializations", Specialization.values());
        return "technicalOperator/form";
    }

    // --- CREATE (Process Form) ---
    @PostMapping
    public String addTechnicalOperator(
            @ModelAttribute @Valid TechnicalOperator technicalOperator,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("specializations", Specialization.values());
            return "technicalOperator/form";
        }

        try {
            technicalOperatorService.save(technicalOperator);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("specializations", Specialization.values());
            return "technicalOperator/form";
        }

        return "redirect:/technical-operators";
    }

    @PostMapping("/remove/{id}")
    public String removeTechnicalOperator(@PathVariable Long id) {
        technicalOperatorService.delete(id);
        return "redirect:/technical-operators";
    }

    @GetMapping("view/{id}")
    public String viewTechnicalOperator(@PathVariable Long id, Model model) {
        return technicalOperatorService.findById(id)
                .map(technicalOperator -> {
                    model.addAttribute("technicalOperator", technicalOperator);
                    return "technicalOperator/view";
                })
                .orElse("redirect:/technical-operators");
    }

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return technicalOperatorService.findById(id)
                .map(technicalOperator -> {
                    model.addAttribute("technicalOperator", technicalOperator);
                    model.addAttribute("specializations", Specialization.values());
                    return "technicalOperator/update";
                })
                .orElse("redirect:/technical-operators");
    }

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateTechnicalOperator(
            @PathVariable Long id,
            @ModelAttribute @Valid TechnicalOperator technicalOperator,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("specializations", Specialization.values());
            return "technicalOperator/update";
        }

        try {
            technicalOperatorService.update(id, technicalOperator);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("specializations", Specialization.values());
            return "technicalOperator/update";
        }

        return "redirect:/technical-operators";
    }
}