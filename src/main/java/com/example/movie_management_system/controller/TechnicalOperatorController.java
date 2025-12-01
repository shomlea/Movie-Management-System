package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.service.TechnicalOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/technical-operators")
public class TechnicalOperatorController {

    private final TechnicalOperatorService technicalOperatorService;

    @Autowired
    public TechnicalOperatorController(TechnicalOperatorService technicalOperatorService) {
        this.technicalOperatorService = technicalOperatorService;
    }

    @GetMapping
    public String listAll(Model model) {
        List<TechnicalOperator> operators = technicalOperatorService.findAll();
        model.addAttribute("technicalOperators", operators);
        return "technicalOperator/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("specializations", Specialization.values());
        return "technicalOperator/form";
    }

    @PostMapping("/add")
    public String addTechnicalOperator(
            @RequestParam("name") String name,
            @RequestParam("salary") double salary,
            @RequestParam("specialization") Specialization specialization
    ) {
        technicalOperatorService.save(name, salary, specialization);
        return "redirect:/technical-operators";
    }

    @PostMapping("/remove/{id}")
    public String removeTechnicalOperator(@PathVariable Long id) {
        technicalOperatorService.delete(id);
        return "redirect:/technical-operators";
    }

    @GetMapping("view/{id}")
    public String viewSeat(@PathVariable Long id, Model model) {
        return technicalOperatorService.findById(id)
                .map(technicalOperator -> {
                    model.addAttribute("technicalOperator", technicalOperator);
                    return "technicalOperator/view";
                })
                .orElse("redirect:/technical-operators");
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return technicalOperatorService.findById(id)
                .map(staff -> {
                    model.addAttribute("technicalOperator", staff);
                    model.addAttribute("specializations", Specialization.values());
                    return "technicalOperator/update";
                })
                .orElse("redirect:/technical-operators");
    }

    @PostMapping("/update/{id}")
    public String updateSupportStaff(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam double salary,
            @RequestParam Specialization specialization) {

        technicalOperatorService.update(id, name, salary, specialization);
        return "redirect:/technical-operators";
    }

}
