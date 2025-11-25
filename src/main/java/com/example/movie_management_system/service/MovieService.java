package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.repository.deprecated.MovieRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepositoryInFile movieRepository;

    public MovieService(MovieRepositoryInFile movieRepository) {
        this.movieRepository = movieRepository;
    }


    public void add(String title, int durationMin, String genre) {
        String id = UUID.randomUUID().toString();
        Movie movie = new Movie(id, title, durationMin, genre);
        movieRepository.add(movie);
    }

    public boolean update(String id, String title,  int durationMin, String genre) {
        Movie movie = new Movie(id, title, durationMin, genre);
        return movieRepository.update(id, movie);
    }

    public void remove(String id) {
        movieRepository.remove(id);
    }

    @Transactional
    public void addScreening(String id, Screening screening){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (!optionalMovie.isPresent()){
            throw new NoSuchElementException("Movie with id " + id + " not found");
        }
        Movie movie = optionalMovie.get();
        movie.addScreening(screening);
        movieRepository.update(id, movie);
    }

    @Transactional
    public boolean removeScreening(String id, String screeningId){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (!optionalMovie.isPresent()){
            throw new NoSuchElementException("Movie with id " + id + " not found");
        }
        Movie movie = optionalMovie.get();
        movie.removeScreening(screeningId);
        movieRepository.update(id, movie);

        return true;
    }

    @Transactional
    public boolean updateScreening(String id, String screeningId, Screening screening){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (!optionalMovie.isPresent()){
            throw new NoSuchElementException("Movie with id " + id + " not found");
        }
        Movie movie = optionalMovie.get();
        movie.updateScreening(screeningId, screening);
        movieRepository.update(id, movie);
        return true;
    }

    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAll() {
        return movieRepository.getAll();
    }
}