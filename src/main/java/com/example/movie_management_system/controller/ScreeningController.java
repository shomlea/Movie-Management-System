package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.service.ScreeningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public String getAllScreenings(Model model) {
        List<Screening> screenings = screeningService.getAll();
        model.addAttribute("screenings", screenings);
        return "screening/index";
    }

    @PostMapping("/remove/{id}")
    public String removeScreening(@PathVariable String id) {
        screeningService.remove(id);
        return "redirect:/screenings";
    }

    @GetMapping("/add")
    public String createForm(){
        return "screening/form";
    }

    @PostMapping
    public String createScreening(@RequestParam String hallId, @RequestParam String movieId, @RequestParam String date) {
        screeningService.save(hallId, movieId, date);
        return "redirect:/screenings";
    }
}
