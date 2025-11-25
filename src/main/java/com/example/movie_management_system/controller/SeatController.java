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
        List<Seat> seats = seatService.findAll();
        model.addAttribute("seats", seats);
        return "seat/index";
    }

    @PostMapping("/remove/{id}")
    public String removeSeat(@PathVariable String id){
        seatService.delete(id);
        return "redirect:/seats";
    }

    @GetMapping("/add")
    public String createForm() {
        return "seat/form";
    }

    @PostMapping
    public String createSeat(@RequestParam String hallId, @RequestParam String seatRow, @RequestParam String seatColumn) {
        seatService.save(hallId, seatRow, seatColumn);
        return "redirect:/seats";
    }

    @GetMapping("view/{id}")
    public String viewSeat(@PathVariable String id, Model model) {
        return seatService.findById(id)
                .map(customer -> {
                    model.addAttribute("seat", customer);
                    return "seat/view";
                })
                .orElse("redirect:/seats");
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        return seatService.findById(id)
                .map(seat -> {
                    model.addAttribute("seat", seat);
                    return "seat/update";
                })
                .orElse("redirect:/seats");
    }

    @PostMapping("/update/{id}")
    public String updateSeat(
            @PathVariable String id,
            @RequestParam String hallId,
            @RequestParam String seatRow,
            @RequestParam String seatColumn) {

        seatService.update(id, hallId, seatRow, seatColumn);
        return "redirect:/seats";
    }

}
