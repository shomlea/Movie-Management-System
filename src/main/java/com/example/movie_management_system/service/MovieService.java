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

    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Transactional
    public Movie update(Long id, Movie updatedMovie) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie with id " + id + " not found"));

        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setDurationMin(updatedMovie.getDurationMin());
        existingMovie.setGenre(updatedMovie.getGenre());

        return movieRepository.save(existingMovie);
    }

    @Transactional
    public void delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new NoSuchElementException("Movie with ID " + id + " not found.");
        }
        movieRepository.deleteById(id);
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
