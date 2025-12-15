package com.example.movie_management_system.controller;

import com.example.movie_management_system.dto.CustomerFilterDto;
import com.example.movie_management_system.dto.TicketFilterDto;
import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.service.ScreeningService;
import com.example.movie_management_system.service.SeatService;
import com.example.movie_management_system.service.TicketService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final SeatService seatService;
    private final ScreeningService screeningService;

    public TicketController(TicketService ticketService, SeatService seatService, ScreeningService screeningService) {
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.screeningService = screeningService;
    }

    @GetMapping
    public String getAllTickets(TicketFilterDto filter, Model model, Pageable pageable) {
        Page<Ticket> ticketPage = ticketService.findAll(filter, pageable);
        model.addAttribute("ticketPage", ticketPage);
        model.addAttribute("filter", filter);
        return "ticket/index";
    }

    @PostMapping("/remove/{id}")
    public String removeTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return "redirect:/tickets";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
        model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
        //model.addAttribute("availableSeats", ticketService.getAvailableSeats());
        return "ticket/form";
    }

    @GetMapping("/buy/{screeningId}")
    public String showBuyForm(Model model, @PathVariable Long screeningId){
        Optional<Screening> screening = screeningService.findById(screeningId);
        if (screening.isEmpty()){
            return "redirect:/screenings";
        }

        Ticket ticket = new Ticket();
        ticket.setScreening(screening.get());

        model.addAttribute("ticket", ticket);
        model.addAttribute("screening", screening.get());
        model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
        model.addAttribute("availableSeats", ticketService.getAvailableSeats(screeningId));
        return "ticket/buy";
    }

    @PostMapping("buy/{screeningId}")
    public String buyTicket(@ModelAttribute @Valid Ticket ticket, BindingResult result, @PathVariable Long screeningId, Model model){
        if (result.hasErrors()){
            Optional<Screening> screening = screeningService.findById(screeningId);
            if (screening.isEmpty()){
                return "redirect:/screenings";
            }
            ticket.setScreening(screening.get());

            model.addAttribute("ticket", ticket);
            model.addAttribute("screening", screening.get());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            model.addAttribute("availableSeats", ticketService.getAvailableSeats(screeningId));
            return "ticket/buy";
        }
        try {
            ticketService.save(ticket);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            Optional<Screening> screening = screeningService.findById(screeningId);
            if (screening.isEmpty()){
                return "redirect:/screenings";
            }

            model.addAttribute("message", e.getMessage());

            ticket.setScreening(screening.get());
            model.addAttribute("ticket", ticket);
            model.addAttribute("screening", screening.get());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            model.addAttribute("availableSeats", ticketService.getAvailableSeats(screeningId));
            return "ticket/buy";
        }

        return "redirect:/tickets";
    }

    @PostMapping
    public String createTicket(
            @ModelAttribute @Valid Ticket ticket,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            //model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/form";
        }

        try {
            ticketService.save(ticket);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            //model.addAttribute("availableSeats", ticketService.getAvailableSeats());
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
                    //model.addAttribute("availableSeats", ticketService.getAvailableSeats());
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
            //model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/update";
        }

        try {
            ticketService.update(id, ticket);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("availableScreenings", ticketService.getAvailableScreenings());
            model.addAttribute("availableCustomers", ticketService.getAvailableCustomers());
            //model.addAttribute("availableSeats", ticketService.getAvailableSeats());
            return "ticket/update";
        }

        return "redirect:/tickets";
    }
}