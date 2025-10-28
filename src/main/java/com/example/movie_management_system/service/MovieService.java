package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.repository.Repository;

import java.util.List;

public class MovieService extends Service<Movie, String> {

    public MovieService(Repository<Movie, String> repository) {
        super(repository);
    }

    @Override
    protected void add(Movie entity) {

    }

    @Override
    protected void remove(Movie entity) {

    }

    @Override
    protected void update(Movie entity) {

    }

    @Override
    protected List<Movie> getAll() {
        return repository.getAll();
    }

    @Override
    protected Movie findById(String id) {
        return repository.findById(id);
    }

}
