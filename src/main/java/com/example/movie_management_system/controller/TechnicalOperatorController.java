package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Specialization;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.service.TechnicalOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/technical-operators")
public class TechnicalOperatorController {

    @Autowired
    private TechnicalOperatorService technicalOperatorService;

    // Adaugă un Technical Operator
    @PostMapping("/add")
    public TechnicalOperator addTechnicalOperator(
            @RequestParam String name,
            @RequestParam int salary,
            @RequestParam Specialization specialization
    ) {
        return technicalOperatorService.addTechnicalOperator(name, salary, specialization);
    }

    // Listează toți Technical Operatorii
    @GetMapping("/all")
    public List<TechnicalOperator> getAllTechnicalOperators() {
        return technicalOperatorService.getAllTechnicalOperator();
    }

    // Caută un Technical Operator după ID
    @GetMapping("/{id}")
    public Optional<TechnicalOperator> getTechnicalOperatorById(@PathVariable String id) {
        return technicalOperatorService.findTechnicalOperatorById(id);
    }

    // Șterge un Technical Operator după ID
    @DeleteMapping("/delete/{id}")
    public String deleteTechnicalOperator(@PathVariable String id) {
        technicalOperatorService.removeTechnicalOperator(id);
        return "Technical Operator with ID " + id + " has been deleted.";
    }

    // GET Salary by Technical Operator ID
    @GetMapping("/{id}/salary")
    public int getSalary(@PathVariable String id) {
        Optional<TechnicalOperator> operator = technicalOperatorService.findTechnicalOperatorById(id);
        return operator.map(TechnicalOperator::getSalary).orElse(0);
    }

}
