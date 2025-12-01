package com.example.movie_management_system.controller;

import org.springframework.ui.Model;
import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/movies")

public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "movie/index";
    }

    @PostMapping("/remove/{id}")
    public String removeMovie(@PathVariable Long id) {
        movieService.delete(id);
        return "redirect:/movies";
    }

    @GetMapping("/add")
    public String createForm(){
        return "movie/form";
    }

    @PostMapping
    public String createMovie(@RequestParam String title, @RequestParam String genre, @RequestParam int durationMin) {
        movieService.save(title, durationMin, genre);
        return "redirect:/movies";
    }
    @GetMapping("view/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        return movieService.findById(id)
                .map(customer -> {
                    model.addAttribute("movie", customer);
                    return "movie/view";
                })
                .orElse("redirect:/movies");
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        return movieService.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    return "movie/update";
                })
                .orElse("redirect:/movies");
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestParam String title, @RequestParam int durationMin, @RequestParam String genre) {
        movieService.update(id, title, durationMin, genre);
        return "redirect:/movies";
    }






}
