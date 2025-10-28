package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.repository.Repository;

import java.util.List;

public class MovieService extends Service<Movie, String> {

    public MovieService(Repository<Movie, String> repository) {
        super(repository);
    }

    @Override
    public List<Movie> getAll() {
        return repository.getAll();
    }

    @Override
    public Movie findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Movie add(Movie movie) {
        repository.add(movie);
        return movie;
    }

    @Override
    public Movie remove(Movie movie) {
        repository.remove(movie);
        return movie;
    }
}
