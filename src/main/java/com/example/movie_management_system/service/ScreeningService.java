package com.example.movie_management_system.service;

import com.example.movie_management_system.dto.ScreeningFilterDto;
import com.example.movie_management_system.model.*;
import com.example.movie_management_system.repository.ScreeningRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Screening> findAll(ScreeningFilterDto filter, Pageable pageable) {

        if (filter.isEmpty()) {
            return screeningRepository.findAll(pageable);
        }

        Specification<Screening> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getMovieTitleQuery() != null && !filter.getMovieTitleQuery().trim().isEmpty()) {
            final String moviePattern = "%" + filter.getMovieTitleQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> {
                Join<Screening, Movie> movieJoin = root.join("movie", JoinType.INNER);
                return cb.like(cb.lower(movieJoin.get("title")), moviePattern);
            });
        }

        if (filter.getHallNameQuery() != null && !filter.getHallNameQuery().trim().isEmpty()) {
            final String hallPattern = "%" + filter.getHallNameQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> {
                Join<Screening, Hall> hallJoin = root.join("hall", JoinType.INNER);
                return cb.like(cb.lower(hallJoin.get("name")), hallPattern);
            });
        }

        if (filter.getTheatreNameQuery() != null && !filter.getTheatreNameQuery().trim().isEmpty()) {
            final String theatrePattern = "%" + filter.getTheatreNameQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> {
                Join<Screening, Hall> hallJoin = root.join("hall", JoinType.INNER);
                Join<Hall, Theatre> theatreJoin = hallJoin.join("theatre", JoinType.INNER);
                return cb.like(cb.lower(theatreJoin.get("name")), theatrePattern);
            });
        }

        if (filter.getDateAfter() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("date"), filter.getDateAfter())
            );
        }

        if (filter.getDateBefore() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("date"), filter.getDateBefore())
            );
        }

        return screeningRepository.findAll(spec, pageable);
    }


}
