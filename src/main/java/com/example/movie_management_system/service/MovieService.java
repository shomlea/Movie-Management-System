package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void add(String title, int durationMin, String genre) {
        String id = UUID.randomUUID().toString();
        Movie movie = new Movie(id, title, durationMin, genre);
        movieRepository.save(movie);
    }

    @Transactional
    public void update(String id, String title, int durationMin, String genre) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie with id " + id + " not found"));

        movie.setTitle(title);
        movie.setDurationMin(durationMin);
        movie.setGenre(genre);
        // No need to call save() — JPA updates automatically
    }

    @Transactional
    public void delete(String id) {
        movieRepository.deleteById(id);
    }

    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
