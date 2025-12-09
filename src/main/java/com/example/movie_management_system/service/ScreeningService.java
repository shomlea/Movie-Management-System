package com.example.movie_management_system.service;

import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Screening save(Screening screening) {
        Long hallId = screening.getHall().getId();
        Long movieId = screening.getMovie().getId();

        Hall hall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        Movie movie = movieService.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("Movie with ID " + movieId + " not found."));

        Optional<Screening> foundScreening = screeningRepository.findByHallIdAndMovieIdAndDate(hall.getId(), movie.getId(), screening.getDate());
        if(foundScreening.isPresent()) {
            throw new DataIntegrityViolationException("There is already a Screening for that movie in that hall on that date");
        }

        screening.setHall(hall);
        screening.setMovie(movie);

        return screeningRepository.save(screening);
    }

    @Transactional
    public Screening update(Long id, Screening updatedScreening) {
        Screening existingScreening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));

        Long newHallId = updatedScreening.getHall().getId();
        Long newMovieId = updatedScreening.getMovie().getId();

        Hall newHall = hallService.findById(newHallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + newHallId + " not found."));

        Movie newMovie = movieService.findById(newMovieId)
                .orElseThrow(() -> new NoSuchElementException("Movie with ID " + newMovieId + " not found."));

        Optional<Screening> foundScreening = screeningRepository.findByHallIdAndMovieIdAndDate(newHallId, newMovieId, updatedScreening.getDate());
        if(foundScreening.isPresent() && !foundScreening.get().getId().equals(existingScreening.getId())) {
            throw new DataIntegrityViolationException("There is already a Screening for that movie in that hall on that date");
        }


        existingScreening.setHall(newHall);
        existingScreening.setMovie(newMovie);
        existingScreening.setDate(updatedScreening.getDate());

        return screeningRepository.save(existingScreening);
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
