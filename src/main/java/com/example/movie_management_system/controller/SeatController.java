package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public String getAllSeats(Model model) {
        List<Seat> seats = seatService.getAllSeats();
        model.addAttribute("seats", seats);
        return "seat/index";
    }

    @PostMapping("/remove/{id}")
    public String removeSeat(@PathVariable String id){
        seatService.removeSeat(id);
        return "redirect:/seats";
    }

    @GetMapping("/add")
    public String createForm() {
        return "seat/form";
    }

    @PostMapping
    public String createSeat(@RequestParam String hallId, @RequestParam String seatRow, @RequestParam String seatColumn) {
        seatService.addSeat(hallId, seatRow, seatColumn);
        return "redirect:/seats";
    }

}
