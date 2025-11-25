package com.example.movie_management_system.service;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final HallService hallService;

    public ScreeningService(ScreeningRepository screeningRepository, MovieService movieService, HallService hallService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.hallService = hallService;
    }
    @Transactional
    public void save(String hallId, String movieId, String date) {
        String id = UUID.randomUUID().toString();
        Screening screening = new Screening(id, hallId, movieId, date);
        if(hallService.findById(hallId).isPresent() && movieService.findById(movieId).isPresent()) {
            screeningRepository.save(screening);
            movieService.addScreening(movieId, screening);
            hallService.addScreening(hallId, screening);
        }
    }
    @Transactional
    public void update(String id, String hallId, String movieId, String date){
        Screening existingScreening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        existingScreening.setHallId(hallId);
        existingScreening.setMovieId(movieId);
        existingScreening.setDate(date);

        movieService.updateScreening(existingScreening.getMovieId(), id, existingScreening);
        hallService.updateScreening(hallId, id, existingScreening);
        screeningRepository.save(existingScreening);
    }

    public List<Movie> getAvailableMovies(){
        return movieService.findAll();
    }

    @Transactional
    public void delete(String id) {
        Optional<Screening> screening = screeningRepository.findById(id);
        if (screening.isEmpty()) {
            throw new NoSuchElementException("Ticket with id " + id + " not found");
        }
        hallService.removeScreening(screening.get().getHallId(), id);
        movieService.removeScreening(screening.get().getMovieId(), id);
        screeningRepository.deleteById(id);


    }

    public Optional<Screening> findById(String id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> findAll() {
        return screeningRepository.findAll();
    }

    @Transactional
    public void addStaffAssignment(String id, StaffAssignment staffAssignment) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        screening.addAssignment(staffAssignment);

        screeningRepository.save(screening);
    }

    @Transactional
    public void removeStaffAssignment(String id, String staffAssignmentId) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        boolean removed = screening.removeAssignment(staffAssignmentId);

        if (removed) {
            screeningRepository.save(screening);
        }

    }

    @Transactional
    public void updateStaffAssignment(String id, String staffAssignmentId, StaffAssignment staffAssignment) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        boolean updated = screening.updateAssignment(staffAssignmentId, staffAssignment);

        if (updated) {
            screeningRepository.save(screening);
        }
    }
}
