package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.service.MovieService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class MovieConverter implements Converter<Long, Movie> {

    private final MovieService movieService;

    public MovieConverter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Movie convert(Long id) {
        if (id == null) return null;

        return movieService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie with ID " + id + " not found."));
    }
}