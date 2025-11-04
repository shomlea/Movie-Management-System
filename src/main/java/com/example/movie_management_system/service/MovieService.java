package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Movie add(String title, int durationMin, String genre) {
        String id = UUID.randomUUID().toString();
        Movie movie = new Movie(id, title, durationMin, genre);
        return movieRepository.add(movie);
    }

    public void remove(String id) {
        movieRepository.remove(id);
    }

    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAll() {
        return movieRepository.getAll();
    }
}