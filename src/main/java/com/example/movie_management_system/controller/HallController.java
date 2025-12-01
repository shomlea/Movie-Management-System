package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/halls")
public class HallController {
    private HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public String getAllHalls(Model model) {
        List<Hall> halls = hallService.findAll();
        model.addAttribute("halls", halls);
        return "hall/index";
    }

    @PostMapping("/remove/{id}")
    public String removeHall(@PathVariable Long id) {
        hallService.delete(id);
        return "redirect:/halls";
    }

    @GetMapping("/add")
    public String createForm(){
        return "hall/form";
    }

    @PostMapping
    public String createHall(@RequestParam String name, @RequestParam Long theatreId, @RequestParam int capacity) {
        hallService.save(name, theatreId, capacity);
        return "redirect:/halls";
    }

    @GetMapping("view/{id}")
    public String viewHall(@PathVariable Long id, Model model) {
        return hallService.findById(id)
                .map(hall -> {
                    model.addAttribute("hall", hall);
                    return "hall/view";
                })
                .orElse("redirect:/halls");
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return hallService.findById(id)
                .map(hall -> {
                    model.addAttribute("hall", hall);
                    return "hall/update";
                })
                .orElse("redirect:/halls");
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestParam String name, @RequestParam Long theatreId, @RequestParam int capacity) {
        hallService.update(id, name, theatreId, capacity);
        return "redirect:/halls";
    }


}
