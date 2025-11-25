package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.model.Screening;
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

    public void update(String id, String title,  int durationMin, String genre) {
        Movie movie = new Movie(id, title, durationMin, genre);
        movieRepository.save(movie);
    }

    public void delete(String id) {
        movieRepository.deleteById(id);
    }

    @Transactional
    public void addScreening(String id, Screening screening){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isEmpty()){
            throw new NoSuchElementException("Movie with id " + id + " not found");
        }
        Movie movie = optionalMovie.get();
        movie.addScreening(screening);
        movieRepository.save(movie);
    }

    @Transactional
    public void removeScreening(String id, String screeningId){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isEmpty()){
            throw new NoSuchElementException("Movie with id " + id + " not found");
        }
        Movie movie = optionalMovie.get();
        movie.removeScreening(screeningId);
        movieRepository.save(movie);

    }

    @Transactional
    public void updateScreening(String id, String screeningId, Screening screening){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isEmpty()){
            throw new NoSuchElementException("Movie with id " + id + " not found");
        }
        Movie movie = optionalMovie.get();
        movie.updateScreening(screeningId, screening);
        movieRepository.save(movie);
    }

    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}