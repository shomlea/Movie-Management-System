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
        // for testing
        movieService.addMovie("1", 2, "1");
        movieService.addMovie("1", 2, "1");
    }

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie/index";
    }

    @PostMapping("/remove/{id}")
    public String removeMovie(@PathVariable String id) {
        movieService.removeMovie(id);
        return "redirect:/movies";
    }

    @GetMapping("/add")
    public String createForm(){
        return "movie/form";
    }

    @PostMapping
    public String createMovie(@RequestParam String title, @RequestParam String genre, @RequestParam int durationMin) {
        movieService.addMovie(title, durationMin, genre);
        return "redirect:/movies";
    }




}
