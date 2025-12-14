package com.example.movie_management_system.service;

import com.example.movie_management_system.dto.MovieFilterDto;
import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Movie> findAll(MovieFilterDto filter, Pageable pageable) {
        if (filter.isEmpty()) {
            return movieRepository.findAll(pageable);
        }

        Specification<Movie> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getTitleQuery() != null && !filter.getTitleQuery().trim().isEmpty()) {
            String titlePattern = "%" + filter.getTitleQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), titlePattern)
            );
        }

        if (filter.getGenreQuery() != null && !filter.getGenreQuery().trim().isEmpty()) {
            String genreQuery = filter.getGenreQuery().trim().toLowerCase();
            // Since genre is likely an exact enum or string field, use cb.equal
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("genre")), genreQuery)
            );
        }

        if (filter.getMinDurationMin() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("durationMin"), filter.getMinDurationMin())
            );
        }

        if (filter.getMaxDurationMin() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("durationMin"), filter.getMaxDurationMin())
            );
        }

        return movieRepository.findAll(spec, pageable);
    }
}
