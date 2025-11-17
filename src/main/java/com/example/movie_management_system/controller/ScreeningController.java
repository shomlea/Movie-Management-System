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
        screeningService.add(hallId, movieId, date);
        return "redirect:/screenings";
    }

    // View a single screening
    @GetMapping("/view/{id}")
    public String viewScreening(@PathVariable String id, Model model) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    return "screening/view";
                })
                .orElse("redirect:/screenings");
    }

    // Show update form
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    return "screening/update";
                })
                .orElse("redirect:/screenings");
    }

    // Submit update
    @PostMapping("/update/{id}")
    public String updateScreening(
            @PathVariable String id,
            @RequestParam String hallId,
            @RequestParam String movieId,
            @RequestParam String date) {

        screeningService.update(id, hallId, movieId, date);
        return "redirect:/screenings";
    }
}

