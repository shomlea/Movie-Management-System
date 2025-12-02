package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.service.TicketService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        return "ticket/index";
    }

    @PostMapping("/remove/{id}")
    public String removeTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return "redirect:/tickets";
    }

    // --- CREATE (Show Form) ---
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
        model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
        model.addAttribute("availableSeats", ticketService.getAvailableSeats()); // Assuming getAvailableSeats() no longer takes ID
        return "ticket/form";
    }

    // --- CREATE (Process Form) ---
    @PostMapping
    public String createTicket(
            @ModelAttribute @Valid Ticket ticket,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/form";
        }

        try {
            ticketService.save(ticket);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/form";
        }

        return "redirect:/tickets";
    }

    @GetMapping("view/{id}")
    public String viewTicket(@PathVariable Long id, Model model) {
        return ticketService.findById(id)
                .map(ticket -> {
                    model.addAttribute("ticket", ticket);
                    return "ticket/view";
                })
                .orElse("redirect:/tickets");
    }

    // --- UPDATE (Show Form) ---
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return ticketService.findById(id)
                .map(ticket -> {
                    model.addAttribute("ticket", ticket);
                    model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
                    model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
                    model.addAttribute("availableSeats", ticketService.getAvailableSeats());
                    return "ticket/update";
                })
                .orElse("redirect:/tickets");
    }

    // --- UPDATE (Process Form) ---
    @PostMapping("/update/{id}")
    public String updateTicket(
            @PathVariable Long id,
            @ModelAttribute @Valid Ticket ticket,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/update";
        }

        try {
            ticketService.update(id, ticket);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/update";
        }

        return "redirect:/tickets";
    }
}