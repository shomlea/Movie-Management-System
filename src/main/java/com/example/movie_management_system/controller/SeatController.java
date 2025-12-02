package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.service.SeatService;
import com.example.movie_management_system.service.HallService; // NEW: Required for dropdown data
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;
    private final HallService hallService; // NEW

    public SeatController(SeatService seatService, HallService hallService) {
        this.seatService = seatService;
        this.hallService = hallService;
    }

    @GetMapping
    public String getAllSeats(Model model) {
        List<Seat> seats = seatService.findAll();
        model.addAttribute("seats", seats);
        return "seat/index";
    }

    @PostMapping("/remove/{id}")
    public String removeSeat(@PathVariable Long id){
        seatService.delete(id);
        return "redirect:/seats";
    }

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("seat", new Seat());
        model.addAttribute("availableHalls", hallService.findAll());
        return "seat/form";
    }

    // --- CREATE (Process Form) ---
    @PostMapping
    public String createSeat(
            @ModelAttribute @Valid Seat seat,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableHalls", hallService.findAll());
            return "seat/form";
        }

        try {
            seatService.save(seat);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableHalls", hallService.findAll());
            return "seat/form";
        }

        return "redirect:/seats";
    }

    @GetMapping("view/{id}")
    public String viewSeat(@PathVariable Long id, Model model) {
        return seatService.findById(id)
                .map(seat -> {
                    model.addAttribute("seat", seat);
                    return "seat/view";
                })
                .orElse("redirect:/seats");
    }

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return seatService.findById(id)
                .map(seat -> {
                    model.addAttribute("seat", seat);
                    model.addAttribute("availableHalls", hallService.findAll());
                    return "seat/update";
                })
                .orElse("redirect:/seats");
    }

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateSeat(
            @PathVariable Long id,
            @ModelAttribute @Valid Seat seat,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableHalls", hallService.findAll());
            return "seat/update";
        }

        try {
            seatService.update(id, seat);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableHalls", hallService.findAll());
            return "seat/update";
        }

        return "redirect:/seats";
    }
}