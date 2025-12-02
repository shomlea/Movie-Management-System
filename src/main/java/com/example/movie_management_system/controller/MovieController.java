package com.example.movie_management_system.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

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
    public String showAddForm(Model model){
        model.addAttribute("movie", new Movie());
        return "movie/form";
    }

    @PostMapping
    public String createMovie(@ModelAttribute @Valid Movie movie, BindingResult result) {
        if (result.hasErrors()) {
            return "movie/form";
        }

        try {
            movieService.save(movie);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            return "movie/form";
        }

        return "redirect:/movies";
    }

    @GetMapping("view/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        return movieService.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
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
    public String updateMovie(
            @PathVariable Long id,
            @ModelAttribute @Valid Movie movie,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "movie/update";
        }

        try {
            movieService.update(id, movie);
        } catch (NoSuchElementException | DataIntegrityViolationException | IllegalArgumentException e) {
            return "movie/update";
        }

        return "redirect:/movies";
    }
}