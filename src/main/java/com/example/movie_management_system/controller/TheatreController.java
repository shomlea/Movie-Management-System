package com.example.movie_management_system.controller;

import com.example.movie_management_system.dto.TheatreFilterDto;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.service.TheatreService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/theatres")
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping
    public String getAllTheatres(TheatreFilterDto filter, Model model, Pageable pageable) {
        Page<Theatre> theatrePage = theatreService.findAll(filter, pageable);
        model.addAttribute("theatrePage", theatrePage);
        model.addAttribute("filter", filter);
        return "theatre/index";
    }

    @PostMapping("/remove/{id}")
    public String removeTheatre(@PathVariable Long id) {
        theatreService.delete(id);
        return "redirect:/theatres";
    }

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("theatre", new Theatre());
        return "theatre/form";
    }

    // --- CREATE (Process Form) ---
    @PostMapping
    public String createTheatre(
            @ModelAttribute @Valid Theatre theatre,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "theatre/form";
        }

        try {
            theatreService.save(theatre);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            return "theatre/form";
        }

        return "redirect:/theatres";
    }

    @GetMapping("view/{id}")
    public String viewTheatre(@PathVariable Long id, Model model) {
        return theatreService.findById(id)
                .map(theatre -> {
                    model.addAttribute("theatre", theatre);
                    return "theatre/view";
                })
                .orElse("redirect:/theatres");
    }

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return theatreService.findById(id)
                .map(theatre -> {
                    model.addAttribute("theatre", theatre);
                    return "theatre/update";
                })
                .orElse("redirect:/theatres");
    }

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateTheatre(
            @PathVariable Long id,
            @ModelAttribute @Valid Theatre theatre,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "theatre/update";
        }

        try {
            theatreService.update(id, theatre);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            return "theatre/update";
        }

        return "redirect:/theatres";
    }
}