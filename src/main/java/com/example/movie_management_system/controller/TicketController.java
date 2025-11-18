package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String createForm(Model model){
        List<Screening> availableScreenings = ticketService.getAvailableScreenings();
        model.addAttribute("availableScreenings", availableScreenings);

        List<Customer> availableCustomers = ticketService.getAvailableCustomers();
        model.addAttribute("availableCustomers", availableCustomers);


        return "ticket/form";
    }

    // TODO implement with dynamic http

//    @GetMapping
//    public void fetchAvailableSeats(@RequestParam String screeningId, Model model) {
//        List<Seat> availableSeats = ticketService.getAvailableSeats(screeningId);
//        model.addAttribute("seats", availableSeats);
//    }

//    // TODO create html page for view
//
//
//    @GetMapping("/view/{id}")
//    public String viewTicket(Model model, @PathVariable String id) {
//        Optional<Ticket> ticket = ticketService.findById(id);
//        if(ticket.isPresent()) {
//            model.addAttribute("ticket", ticket.get());
//            return "ticket/view";
//        }
//        return "redirect:/tickets";
//
//    }
//
//    // TODO create html page for edit
//
//    @GetMapping("/update/{id}")
//    public String createUpdateForm(Model model, @PathVariable String id) {
//        Optional<Ticket> ticket = ticketService.findById(id);
//        if(ticket.isPresent()) {
//            model.addAttribute("ticket", ticket.get());
//            return "ticket/edit";
//        }
//        return "redirect:/tickets";
//
//    }

    // TODO create update function

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable String id, @RequestParam String screeningId, @RequestParam String customerId, @RequestParam String seatId, @RequestParam double price) {
        Optional<Ticket> ticket = ticketService.findById(id);
        ticketService.update(id, screeningId, customerId, seatId, price);
        return "redirect:/tickets";
    }


    @PostMapping
        public String createTicket(@RequestParam String screeningId, @RequestParam String customerId, @RequestParam String seatId, @RequestParam double price) {
        ticketService.add(screeningId, customerId, seatId, price);
        return "redirect:/tickets";
    }

    @GetMapping("view/{id}")
    public String viewTicket(@PathVariable String id, Model model) {
        return ticketService.findById(id)
                .map(ticket -> {
                    model.addAttribute("ticket", ticket);
                    return "ticket/view";
                })
                .orElse("redirect:/tickets");
    }

}
