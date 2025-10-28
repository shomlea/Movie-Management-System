package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository extends Repository<Movie, String>{

    @Override
    public void add(Movie entity) {

    }

    @Override
    public void remove(Movie entity) {

    }

    @Override
    public Movie findById(String id){
        return null;
    }

    @Override
    public List<Movie> getAll() {
        return List.of();
    }
}