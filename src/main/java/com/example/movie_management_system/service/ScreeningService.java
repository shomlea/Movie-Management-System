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

    public ScreeningService(ScreeningRepository screeningRepository,
                            MovieService movieService,
                            HallService hallService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @Transactional
    public void save(String hallId, String movieId, String date) {

        hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall not found"));

        movieService.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));

        Screening screening = new Screening(
                UUID.randomUUID().toString(), hallId, movieId, date
        );

        screeningRepository.save(screening);
    }

    @Transactional
    public void update(String id, String hallId, String movieId, String date) {

        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        // Validate foreign keys again
        hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall not found"));

        movieService.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));

        screening.setHallId(hallId);
        screening.setMovieId(movieId);
        screening.setDate(date);

    }

    @Transactional
    public void delete(String id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        screeningRepository.delete(screening);
    }

    public List<Movie> getAvailableMovies() {
        return movieService.findAll();
    }

    @Transactional
    public Optional<Screening> findById(String id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> findAll() {
        return screeningRepository.findAll();
    }


    @Transactional
    public void addStaffAssignment(String id, StaffAssignment staffAssignment) {

        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        screening.addAssignment(staffAssignment);
        // screeningRepository.save(screening); // Not needed if mapped correctly
    }

    @Transactional
    public void removeStaffAssignment(String id, String staffAssignmentId) {

        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        if (screening.removeAssignment(staffAssignmentId)) {
            // auto-update inside transaction
        }
    }

    @Transactional
    public void updateStaffAssignment(String id, String staffAssignmentId, StaffAssignment updated) {

        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        if (screening.updateAssignment(staffAssignmentId, updated)) {
            // auto-update
        }
    }
}
