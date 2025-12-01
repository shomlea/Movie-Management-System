package com.example.movie_management_system.service;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Screening save(Long hallId, Long movieId, LocalDate date) {

        Hall hall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall not found"));

        Movie movie = movieService.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));

        Screening screening = new Screening(hall, movie, date);

        return screeningRepository.save(screening);
    }

    @Transactional
    public Screening update(Long id, Long hallId, Long movieId, LocalDate date) {

        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        // Validate foreign keys again
        Hall hall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall not found"));

        Movie movie = movieService.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));


        screening.setHall(hall);
        screening.setMovie(movie);
        screening.setDate(date);

        return screeningRepository.save(screening);

    }

    @Transactional
    public void delete(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found"));

        screeningRepository.delete(screening);
    }

    public List<Movie> getAvailableMovies() {
        return movieService.findAll();
    }

    public Optional<Screening> findById(Long id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> findAll() {
        return screeningRepository.findAll();
    }


//    @Transactional
//    public void addStaffAssignment(String id, StaffAssignment staffAssignment) {
//
//        Screening screening = screeningRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Screening not found"));
//
//        screening.addAssignment(staffAssignment);
//        // screeningRepository.save(screening); // Not needed if mapped correctly
//    }
//
//    @Transactional
//    public void removeStaffAssignment(String id, String staffAssignmentId) {
//
//        Screening screening = screeningRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Screening not found"));
//
//        if (screening.removeAssignment(staffAssignmentId)) {
//            // auto-update inside transaction
//        }
//    }
//
//    @Transactional
//    public void updateStaffAssignment(String id, String staffAssignmentId, StaffAssignment updated) {
//
//        Screening screening = screeningRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Screening not found"));
//
//        if (screening.updateAssignment(staffAssignmentId, updated)) {
//            // auto-update
//        }
//    }
}
