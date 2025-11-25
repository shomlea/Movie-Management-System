package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Movie;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary

public class MovieRepositoryInFile extends BaseRepositoryInFile<Movie, String>{
    public MovieRepositoryInFile() {
        super(Movie::getId, "data/movies.json", Movie.class);
    }
}
