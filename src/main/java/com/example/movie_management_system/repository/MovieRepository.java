package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private List<Movie> movies;

    public MovieRepository() {
        movies = new ArrayList<>();
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie getMovieById(String id) {
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
}