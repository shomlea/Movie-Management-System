package com.example.movie_management_system.controller;

import com.example.movie_management_system.dto.HallFilterDto;
import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.service.HallService;
import com.example.movie_management_system.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/halls")
public class HallController {
    private HallService hallService;
    private TheatreService theatreService;

    public HallController(HallService hallService,  TheatreService theatreService) {
        this.hallService = hallService;
        this.theatreService = theatreService;
    }

    @GetMapping
    public String getAllHalls(HallFilterDto filter, Model model, @PageableDefault(size = 15) Pageable pageable) {
        Page<Hall> hallPage = hallService.findAll(filter, pageable);
        model.addAttribute("hallPage", hallPage);
        model.addAttribute("filter", filter);
        return "hall/index";
    }

    @PostMapping("/remove/{id}")
    public String removeHall(@PathVariable Long id) {
        hallService.delete(id);
        return "redirect:/halls";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hall", new Hall());
        model.addAttribute("availableTheatres", theatreService.findAll());
        return "hall/form";
    }

    @PostMapping
    public String createHall(
            @ModelAttribute @Valid Hall hall,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableTheatres", theatreService.findAll());
            return "hall/form";
        }

        try {
            hallService.save(hall);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableTheatres", theatreService.findAll());
            return "hall/form";
        }

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
    public String showUpdateForm(Model model, @PathVariable Long id) {
        return hallService.findById(id)
                .map(hall -> {
                    model.addAttribute("hall", hall);
                    model.addAttribute("availableTheatres", theatreService.findAll()); // Required for dropdown
                    return "hall/update";
                })
                .orElse("redirect:/halls");
    }

    @PostMapping("/update/{id}")
    public String updateHall(
            @PathVariable Long id,
            @ModelAttribute @Valid Hall hall,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableTheatres", theatreService.findAll());
            return "hall/update";
        }

        try {

            hallService.update(id, hall);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableTheatres", theatreService.findAll());
            return "hall/update";
        }

        return "redirect:/halls";
    }


}
