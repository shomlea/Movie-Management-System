package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        List<Ticket> tickets = ticketService.getAll();
        model.addAttribute("tickets", tickets);
        return "ticket/index";
    }

    @PostMapping("/remove/{id}")
    public String removeTicket(@PathVariable String id) {
        ticketService.remove(id);
        return "redirect:/tickets";
    }

    @GetMapping("/add")
    public String createForm(){
        return "ticket/form";
    }

    @PostMapping
        public String createTicket(@RequestParam String screeningId, @RequestParam String customerId, @RequestParam String seatId, @RequestParam double price) {
        ticketService.add(screeningId, customerId, seatId, price);
        return "redirect:/tickets";
    }

}
