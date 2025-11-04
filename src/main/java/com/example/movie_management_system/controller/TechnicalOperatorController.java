package com.example.movie_management_system.controller;

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
        List<TechnicalOperator> operators = technicalOperatorService.getAllTechnicalOperator();
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
            @RequestParam("salary") int salary,
            @RequestParam("specialization") Specialization specialization
    ) {
        technicalOperatorService.addTechnicalOperator(name, salary, specialization);
        return "redirect:/technical-operators";
    }

    @PostMapping("/remove/{id}")
    public String removeTechnicalOperator(@PathVariable String id) {
        technicalOperatorService.removeTechnicalOperator(id);
        return "redirect:/technical-operators";
    }

}
