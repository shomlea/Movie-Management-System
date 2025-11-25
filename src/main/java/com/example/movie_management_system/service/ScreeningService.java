package com.example.movie_management_system.service;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.deprecated.ScreeningRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScreeningService {
    private final ScreeningRepositoryInFile screeningRepository;
    private final MovieService movieService;
    private final HallService hallService;

    public ScreeningService(ScreeningRepositoryInFile screeningRepository, MovieService movieService, HallService hallService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.hallService = hallService;
    }
    @Transactional
    public void add(String hallId, String movieId, String date) {
        String id = UUID.randomUUID().toString();
        Screening screening = new Screening(id, hallId, movieId, date);
        if(hallService.findById(hallId).isPresent() && movieService.findById(movieId).isPresent()) {
            screeningRepository.add(screening);
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
        screeningRepository.update(id, existingScreening);
    }

    public List<Movie> getAvailableMovies(){
        return movieService.getAll();
    }

    @Transactional
    public boolean remove(String id) {
        Optional<Screening> screening = screeningRepository.findById(id);
        if (!screening.isPresent()) {
            throw new NoSuchElementException("Ticket with id " + id + " not found");
        }
        hallService.removeScreening(screening.get().getHallId(), id);
        movieService.removeScreening(screening.get().getMovieId(), id);
        screeningRepository.remove(id);

        return true;
    }

    public Optional<Screening> findById(String id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> getAll() {
        return screeningRepository.getAll();
    }

    @Transactional
    public void addStaffAssignment(String id, StaffAssignment staffAssignment) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        screening.addAssignment(staffAssignment);

        screeningRepository.update(id, screening);
    }

    @Transactional
    public boolean removeStaffAssignment(String id, String staffAssignmentId) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        boolean removed = screening.removeAssignment(staffAssignmentId);

        if (removed) {
            screeningRepository.update(id, screening);
        }
        return removed;
    }

    @Transactional
    public boolean updateStaffAssignment(String id, String staffAssignmentId, StaffAssignment staffAssignment) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        boolean updated = screening.updateAssignment(staffAssignmentId, staffAssignment);

        if (updated) {
            screeningRepository.update(id, screening);
        }
        return updated;
    }
}
