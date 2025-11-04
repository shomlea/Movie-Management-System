package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Theatre;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TheatreRepositoryInMemory extends BaseRepositoryInMemory<Theatre, String> {
    public TheatreRepositoryInMemory() {
        super(Theatre::getId);
    }
}
