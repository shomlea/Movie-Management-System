package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Movie;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class MovieRepositoryInMemory extends BaseRepositoryInMemory<Movie, String> {
    public MovieRepositoryInMemory() {
        super(Movie::getId);
    }
}
