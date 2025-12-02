package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.service.ScreeningService;
import com.example.movie_management_system.service.HallService; // NEW: Required for dropdown data
import com.example.movie_management_system.service.MovieService; // NEW: Required for dropdown data
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;
    private final HallService hallService; // NEW
    private final MovieService movieService; // NEW

    public ScreeningController(ScreeningService screeningService, HallService hallService, MovieService movieService) {
        this.screeningService = screeningService;
        this.hallService = hallService;
        this.movieService = movieService;
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

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("screening", new Screening());
        model.addAttribute("availableHalls", hallService.findAll());
        model.addAttribute("availableMovies", movieService.findAll());
        return "screening/form";
    }

    // --- CREATE (Process Form) ---
    @PostMapping
    public String createScreening(
            @ModelAttribute @Valid Screening screening,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableHalls", hallService.findAll());
            model.addAttribute("availableMovies", movieService.findAll());
            return "screening/form";
        }

        try {
            screeningService.save(screening);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableHalls", hallService.findAll());
            model.addAttribute("availableMovies", movieService.findAll());
            return "screening/form";
        }

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

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    model.addAttribute("availableHalls", hallService.findAll());
                    model.addAttribute("availableMovies", movieService.findAll());
                    return "screening/update";
                })
                .orElse("redirect:/screenings");
    }

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateScreening(
            @PathVariable Long id,
            @ModelAttribute @Valid Screening screening,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableHalls", hallService.findAll());
            model.addAttribute("availableMovies", movieService.findAll());
            return "screening/update";
        }

        try {
            screeningService.update(id, screening);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableHalls", hallService.findAll());
            model.addAttribute("availableMovies", movieService.findAll());
            return "screening/update";
        }

        return "redirect:/screenings";
    }
}