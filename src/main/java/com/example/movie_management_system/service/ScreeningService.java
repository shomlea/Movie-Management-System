package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.repository.MovieRepositoryInFile;
import com.example.movie_management_system.repository.ScreeningRepositoryInFile;
import com.example.movie_management_system.repository.deprecated.ScreeningRepositoryInMemory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScreeningService {
    private final ScreeningRepositoryInFile screeningRepository;
    private final MovieService movieService;

    public ScreeningService(ScreeningRepositoryInFile screeningRepository, MovieService movieService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
    }

    public void add(String hallId, String movieId, String date) {
        String id = UUID.randomUUID().toString();
        Screening screening = new Screening(id, hallId, movieId, date);
        screeningRepository.add(screening);
    }

    public boolean update(String id, String hallId, String movieId, String date){
        Screening screening = new Screening(id, hallId, movieId, date);
        return screeningRepository.update(id, screening);
    }

    public List<Movie> getAvailableMovies(){
        return movieService.getAll();
    }


    public void remove(String id) {
        screeningRepository.remove(id);
    }

    public Optional<Screening> findById(String id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> getAll() {
        return screeningRepository.getAll();
    }
}
