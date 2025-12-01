package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.service.ScreeningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        List<Screening> screenings = screeningService.findAll();
        model.addAttribute("screenings", screenings);
        return "screening/index";
    }

    @PostMapping("/remove/{id}")
    public String removeScreening(@PathVariable Long id) {
        screeningService.delete(id);
        return "redirect:/screenings";
    }

    @GetMapping("/add")
    public String createForm(){
        return "screening/form";
    }

    @PostMapping
    public String createScreening(@RequestParam Long hallId, @RequestParam Long movieId, @RequestParam LocalDate date) {
        screeningService.save(hallId, movieId, date);
        return "redirect:/screenings";
    }

    @GetMapping("/view/{id}")
    public String viewScreening(@PathVariable Long id, Model model) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    return "screening/view";
                })
                .orElse("redirect:/screenings");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    return "screening/update";
                })
                .orElse("redirect:/screenings");
    }

    @PostMapping("/update/{id}")
    public String updateScreening(
            @PathVariable Long id,
            @RequestParam Long hallId,
            @RequestParam Long movieId,
            @RequestParam LocalDate date) {

        screeningService.update(id, hallId, movieId, date);
        return "redirect:/screenings";
    }
}

