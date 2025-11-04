package com.example.movie_management_system.controller;

import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.service.TheatreService;
import org.springframework.ui.Model;
import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/theatres")

public class TheatreController {
    private TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping
    public String getAllTheatres(Model model) {
        List<Theatre> theatres = theatreService.getAll();
        model.addAttribute("theatres", theatres);
        return "theatre/index";
    }

    @PostMapping("/remove/{id}")
    public String removeTheatre(@PathVariable String id) {
        theatreService.remove(id);
        return "redirect:/theatres";
    }

    @GetMapping("/add")
    public String createForm(){
        return "theatre/form";
    }

    @PostMapping
    public String createTheatre(@RequestParam String name, @RequestParam String city, @RequestParam int parkingCapacity) {
        theatreService.add(name,city, parkingCapacity);
        return "redirect:/theatres";
    }




}
