package com.example.movie_management_system.controller;

import com.example.movie_management_system.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")

public class HelloController {
    private MovieService movieService;

    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
